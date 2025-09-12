package com.hrudhaykanth116.auth.data.repository.user

import com.hrudhaykanth116.auth.data.models.UserData
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper

interface IUserRepository {

    suspend fun getUserData(): RepoResultWrapper<UserData>

}