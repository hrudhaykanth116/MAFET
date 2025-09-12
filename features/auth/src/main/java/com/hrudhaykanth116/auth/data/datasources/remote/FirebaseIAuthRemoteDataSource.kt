package com.hrudhaykanth116.auth.data.datasources.remote

import android.graphics.Bitmap
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.common.utils.await
import com.hrudhaykanth116.core.common.utils.awaitOrNull
import com.hrudhaykanth116.core.data.models.ApiError
import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
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

    override suspend fun getLoggedInUserId(): ApiResultWrapper<String> = withContext(dispatcher) {
        val currentUser = firebaseAuth.currentUser

        return@withContext if (currentUser != null) {
            ApiResultWrapper.Success(
                currentUser.uid
            )
        } else {
            ApiResultWrapper.Error(
                ApiError.SomethingWentWrong
            )
        }

    }

    override suspend fun login(loginRequest: LoginRequest): ApiResultWrapper<LoginResult> =
        withContext(dispatcher) {
            val signInResult: RepoResultWrapper<AuthResult> = firebaseAuth.signInWithEmailAndPassword(
                loginRequest.email,
                loginRequest.password
            ).await()

            return@withContext when (signInResult) {
                is RepoResultWrapper.Error -> {
                    ApiResultWrapper.Error(ApiError.SomethingWentWrong)
                }

                is RepoResultWrapper.Success -> {
                    ApiResultWrapper.Success(
                        LoginResult(
                            signInResult.data.user?.uid!!,
                            "Successfully logged in".toUIText()
                        )
                    )
                }
            }
        }

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResultWrapper<SignUpResult> =
        withContext(dispatcher) {
            val signInResult: RepoResultWrapper<AuthResult> = firebaseAuth.createUserWithEmailAndPassword(
                signUpRequest.email,
                signUpRequest.password
            ).await()


            return@withContext when (signInResult) {
                is RepoResultWrapper.Error -> {
                    ApiResultWrapper.Error(ApiError.SomethingWentWrong)
                }

                is RepoResultWrapper.Success -> {

                    val user = signInResult.data.user
                        ?: return@withContext ApiResultWrapper.Error(ApiError.SomethingWentWrong)

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
                            is RepoResultWrapper.Error -> {

                            }

                            is RepoResultWrapper.Success -> {
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

                    ApiResultWrapper.Success(
                        SignUpResult(
                            message = UIText.Text("Successfully signed up")
                        )
                    )
                }
            }
        }

    override suspend fun logout(): ApiResultWrapper<UIText> {
        firebaseAuth.signOut()
        return ApiResultWrapper.Success(UIText.Text("Signed out successfully"))
    }

    companion object {
        private const val TAG = "FirebaseAuthRemoteDataS"
    }


}