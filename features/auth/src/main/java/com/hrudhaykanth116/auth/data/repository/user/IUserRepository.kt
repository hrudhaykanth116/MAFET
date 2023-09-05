package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.data.models.DataResult

interface IUserRepository {

    suspend fun getUserData(): DataResult<UserData>

}