package com.hrudhaykanth116.auth.data.repository

import com.hrudhaykanth116.auth.data.datasources.remote.IAuthRemoteDataSource
import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: IAuthRemoteDataSource,
) : IAuthRepository {

    private val dispatcher = Dispatchers.IO

    override suspend fun getLoggedInUser(): DataResult<String> = withContext(dispatcher) {
        return@withContext authRemoteDataSource.getLoggedInUserId()
    }

    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResult> =
        withContext(dispatcher) {
            return@withContext authRemoteDataSource.login(loginRequest)
        }

    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResult> =
        withContext(dispatcher) {
            return@withContext authRemoteDataSource.signUp(signUpRequest)
        }

    override suspend fun logout(): DataResult<UIText> = withContext(dispatcher) {
        return@withContext authRemoteDataSource.logout()
    }


}