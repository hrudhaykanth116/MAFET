package com.hrudhaykanth116.auth.data.datasources.remote

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText

interface IAuthRemoteDataSource {

    suspend fun getLoggedInUserId(): DataResult<String>
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResult>
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResult>
    suspend fun logout(): DataResult<UIText>

}