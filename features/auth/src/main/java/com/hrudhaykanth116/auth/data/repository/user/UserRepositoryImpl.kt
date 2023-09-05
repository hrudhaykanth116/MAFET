package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.datasources.remote.user.IUserRemoteDataSource
import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.data.models.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: IUserRemoteDataSource,
) : IUserRepository {

    private val dispatcher = Dispatchers.IO

    override suspend fun getUserData(): DataResult<UserData> = withContext(dispatcher) {
        return@withContext remoteDataSource.getUserData()
    }
}