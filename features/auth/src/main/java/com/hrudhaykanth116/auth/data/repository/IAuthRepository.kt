package com.hrudhaykanth116.auth.data.repository

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.models.SignUpResult
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.models.UIText

interface IAuthRepository {

    suspend fun getLoggedInUser(): RepoResultWrapper<String>
    suspend fun login(loginRequest: LoginRequest): RepoResultWrapper<LoginResult>
    suspend fun signUp(signUpRequest: SignUpRequest): RepoResultWrapper<SignUpResult>
    suspend fun logout(): RepoResultWrapper<UIText>

}