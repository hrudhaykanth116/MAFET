package com.hrudhaykanth116.mafet.auth.data.repository

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.mafet.auth.data.models.LoginRequest
import com.hrudhaykanth116.mafet.auth.data.models.LoginResult
import com.hrudhaykanth116.mafet.auth.data.models.SignUpRequest
import com.hrudhaykanth116.mafet.auth.data.models.SignUpResult

interface IAuthRepository {

    suspend fun getLoggedInUser(): DataResult<String>
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResult>
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<SignUpResult>
    suspend fun logout(): DataResult<UIText>

}