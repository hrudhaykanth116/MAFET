package com.hrudhaykanth116.auth.data.datasources.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.common.utils.awaitOrNull
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText

class UserRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val database: DatabaseReference,
    private val firebaseStorage: FirebaseStorage,
) : IUserRemoteDataSource {

    override suspend fun getUserData(): DataResult<UserData> {

        val user = firebaseAuth.currentUser
            ?: return DataResult.Error(uiMessage = UIText.Text("No user details found"))

        // TODO: Use data models to put data and handle exceptions while put operation

        val userNode = database.child("data").child(user.uid)

        // TODO: Catch parse exceptions
        val userData: UserData =
            userNode.get().awaitOrNull()?.getValue(UserData::class.java) ?: return DataResult.Error(
                uiMessage = UIText.Text("No user details found")
            )

        // TODO: Use async for parallel work.
        // val userName: String = userNode.child("userName").get().awaitOrNull()?.getValue(String::class.java) ?: "No display name found"
        // val bio = userNode.child("bio").get().awaitOrNull()
        // val imgUri = userNode.child("profileImgUrl").get().awaitOrNull()?.getValue(String::class.java)

        return DataResult.Success(userData)


    }
}