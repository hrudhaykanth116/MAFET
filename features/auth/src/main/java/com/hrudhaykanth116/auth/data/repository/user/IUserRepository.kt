package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.domain.result.DomainResult

interface IUserRepository {

    suspend fun getUserData(): DomainResult<UserData>

}