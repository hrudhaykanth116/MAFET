package com.hrudhaykanth116.mafet.auth.data.repository.user

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.mafet.auth.data.models.UserData

interface IUserRepository {

    suspend fun getUserData(): DataResult<UserData>

}