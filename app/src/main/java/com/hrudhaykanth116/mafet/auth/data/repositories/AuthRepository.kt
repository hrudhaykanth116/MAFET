package com.hrudhaykanth116.mafet.auth.data.repositories

import com.hrudhaykanth116.mafet.auth.data.data_models.LoginResponse
import com.hrudhaykanth116.mafet.auth.data.data_models.SignUpResponse
import com.hrudhaykanth116.mafet.auth.data.data_sources.local.AuthLocalDataSource
import com.hrudhaykanth116.mafet.auth.data.data_sources.remote.AuthRemoteDataSource
import com.hrudhaykanth116.mafet.common.remote.DataResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    suspend fun signUp(name: String, email: String, password: String): DataResource<SignUpResponse> {
        return DataResource.success(SignUpResponse("Successfully signed up."))
    }

    suspend fun login(email: String, password: String): DataResource<LoginResponse>{
        return DataResource.success(LoginResponse("Successfully logged in."))
    }

}