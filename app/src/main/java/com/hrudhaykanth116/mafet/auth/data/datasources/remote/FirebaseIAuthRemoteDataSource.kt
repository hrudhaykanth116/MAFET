package com.hrudhaykanth116.mafet.auth.data.datasources.remote

import android.graphics.Bitmap
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.utils.await
import com.hrudhaykanth116.core.utils.awaitOrNull
import com.hrudhaykanth116.mafet.auth.data.models.LoginRequest
import com.hrudhaykanth116.mafet.auth.data.models.LoginResult
import com.hrudhaykanth116.mafet.auth.data.models.SignUpRequest
import com.hrudhaykanth116.mafet.auth.data.models.SignUpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class FirebaseIAuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: DatabaseReference,
    private val firebaseStorage: FirebaseStorage,
) : IAuthRemoteDataSource {

    private val dispatcher = Dispatchers.IO

    override suspend fun getLoggedInUserId(): DataResult<String> = withContext(dispatcher) {
        val currentUser = firebaseAuth.currentUser

        return@withContext if (currentUser != null) {
            DataResult.Success(
                currentUser.uid
            )
        } else {
            DataResult.Error(
                fullDescription = "No logged in user found."
            )
        }

    }

    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResult> =
        withContext(dispatcher) {
            val signInResult: DataResult<AuthResult> = firebaseAuth.signInWithEmailAndPassword(
                loginRequest.email,
                loginRequest.password
            ).await()

            return@withContext when (signInResult) {
                is DataResult.Error -> signInResult
                is DataResult.Success -> {
                    DataResult.Success(
                        LoginResult(
                            // TODO: Handle null cases
                            userId = signInResult.data.user?.uid!!,
                            message = UIText.Text("Logged in successfully")
                        )
                    )
                }
            }
        }

    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResult> =
        withContext(dispatcher) {
            val signInResult: DataResult<AuthResult> = firebaseAuth.createUserWithEmailAndPassword(
                signUpRequest.email,
                signUpRequest.password
            ).await()


            return@withContext when (signInResult) {
                is DataResult.Error -> signInResult
                is DataResult.Success -> {

                    val user = signInResult.data.user
                        ?: return@withContext DataResult.Error(UIText.Text("Something went wrong"))

                    val userNode = database.child("data").child(user.uid)

                    // TODO: Use async for parallel work.
                    userNode.child("userName").setValue(signUpRequest.userName).await()
                    userNode.child("bio").setValue(signUpRequest.bio).await()

                    // database.push()

                    signUpRequest.imgBitmap?.let {
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        it.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                        val data = byteArrayOutputStream.toByteArray()

                        val profileImageRef =
                            firebaseStorage.reference.child("${user.uid}/profileImg.jpg")
                        val result = profileImageRef.putBytes(data).await()
                        when (result) {
                            is DataResult.Error -> {

                            }
                            is DataResult.Success -> {
                                val url = profileImageRef.downloadUrl.awaitOrNull()?.toString()
                                userNode.child("profileImgUrl").setValue(url).await()
                            }
                        }
                    }

                    // signInResult.data.user?.updateProfile(
                    //     UserProfileChangeRequest.Builder().setPhotoUri().build()
                    // )?.await()

                    val firebaseUser = signInResult.data.user
                    // val displayName =
                    //     firebaseUser?.updateProfile(UserProfileChangeRequest()) ?: "No display name"

                    DataResult.Success(
                        SignUpResult(
                            message = UIText.Text("Successfully signed up")
                        )
                    )
                }
            }
        }

    override suspend fun logout(): DataResult<UIText> {
        firebaseAuth.signOut()
        return DataResult.Success(UIText.Text("Signed out successfully"))
    }

    companion object {
        private const val TAG = "FirebaseAuthRemoteDataS"
    }


}