package com.hrudhaykanth116.auth.data.repository

import com.hrudhaykanth116.auth.data.datasources.remote.IAuthRemoteDataSource
import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.ui.models.UIText

class AuthRepositoryImpl(
    private val authRemoteDataSource: IAuthRemoteDataSource,
) : IAuthRepository, BaseRepository() {

    override suspend fun getLoggedInUser(): DomainResult<String> = fetchResult {
        authRemoteDataSource.getLoggedInUserId()
    }

    override suspend fun login(loginRequest: LoginRequest): DomainResult<LoginResult> =
        fetchResult {
            authRemoteDataSource.login(loginRequest)
        }

    override suspend fun signUp(signUpRequest: SignUpRequest): DomainResult<SignUpResult> =
        fetchResult {
            authRemoteDataSource.signUp(signUpRequest)
        }

    override suspend fun logout(): DomainResult<UIText> = fetchResult {
        authRemoteDataSource.logout()
    }


}