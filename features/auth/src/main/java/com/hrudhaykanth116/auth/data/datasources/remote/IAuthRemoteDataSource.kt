package com.hrudhaykanth116.auth.data.datasources.remote

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.core.ui.models.UIText

interface IAuthRemoteDataSource {

    suspend fun getLoggedInUserId(): ApiResultWrapper<String>
    suspend fun login(loginRequest: LoginRequest): ApiResultWrapper<LoginResult>
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResultWrapper<SignUpResult>
    suspend fun logout(): ApiResultWrapper<UIText>

}