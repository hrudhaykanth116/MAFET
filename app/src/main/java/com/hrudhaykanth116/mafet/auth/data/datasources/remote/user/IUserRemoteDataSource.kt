package com.hrudhaykanth116.mafet.auth.data.datasources.remote.user

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.mafet.auth.data.models.UserData

interface IUserRemoteDataSource {

    suspend fun getUserData(): DataResult<UserData>

}