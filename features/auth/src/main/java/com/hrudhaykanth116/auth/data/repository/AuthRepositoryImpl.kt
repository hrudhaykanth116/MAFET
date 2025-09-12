package com.hrudhaykanth116.auth.data.repository

import com.hrudhaykanth116.auth.data.datasources.remote.IAuthRemoteDataSource
import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: IAuthRemoteDataSource,
) : IAuthRepository, BaseRepository() {

    override suspend fun getLoggedInUser(): RepoResultWrapper<String> = getResult {
        authRemoteDataSource.getLoggedInUserId()
    }

    override suspend fun login(loginRequest: LoginRequest): RepoResultWrapper<LoginResult> =
        getResult {
            authRemoteDataSource.login(loginRequest)
        }

    override suspend fun signUp(signUpRequest: SignUpRequest): RepoResultWrapper<SignUpResult> =
        getResult {
            authRemoteDataSource.signUp(signUpRequest)
        }

    override suspend fun logout(): RepoResultWrapper<UIText> = getResult {
        authRemoteDataSource.logout()
    }


}