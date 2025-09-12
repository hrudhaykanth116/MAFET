package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.datasources.remote.user.IUserRemoteDataSource
import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: IUserRemoteDataSource,
    @IoDispatcher private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : IUserRepository, BaseRepository() {

    override suspend fun getUserData(): RepoResultWrapper<UserData> = getResult {
        remoteDataSource.getUserData()
    }
}