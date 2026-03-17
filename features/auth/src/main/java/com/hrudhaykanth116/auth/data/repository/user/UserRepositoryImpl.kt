package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.datasources.remote.user.IUserRemoteDataSource
import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepositoryImpl(
    private val remoteDataSource: IUserRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IUserRepository, BaseRepository() {

    override suspend fun getUserData(): RepoResultWrapper<UserData> = getResult {
        remoteDataSource.getUserData()
    }
}