package com.hrudhaykanth116.auth.data.datasources.remote.user

import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.data.models.DataResult

interface IUserRemoteDataSource {

    suspend fun getUserData(): DataResult<UserData>

}