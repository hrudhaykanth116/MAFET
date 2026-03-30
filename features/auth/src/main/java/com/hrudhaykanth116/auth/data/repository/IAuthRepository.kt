package com.hrudhaykanth116.auth.data.repository

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.models.UIText

interface IAuthRepository {

    suspend fun getLoggedInUser(): DomainResult<String>
    suspend fun login(loginRequest: LoginRequest): DomainResult<LoginResult>
    suspend fun signUp(signUpRequest: SignUpRequest): DomainResult<SignUpResult>
    suspend fun logout(): DomainResult<UIText>

}