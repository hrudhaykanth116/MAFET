package com.hrudhaykanth116.mafet.auth.data.repositories

import com.hrudhaykanth116.mafet.auth.data.data_models.LoginResponse
import com.hrudhaykanth116.mafet.auth.data.data_models.SignUpResponse
import com.hrudhaykanth116.mafet.auth.data.data_sources.local.AuthLocalDataSource
import com.hrudhaykanth116.mafet.auth.data.data_sources.remote.AuthRemoteDataSource
import com.hrudhaykanth116.core.data.models.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) {

    suspend fun signUp(name: String, email: String, password: String): com.hrudhaykanth116.core.data.models.DataResult<SignUpResponse> {
        return withContext(Dispatchers.IO){
            com.hrudhaykanth116.core.data.models.DataResult.Success(SignUpResponse("Successfully signed up."))
        }
    }

    suspend fun login(email: String, password: String): com.hrudhaykanth116.core.data.models.DataResult<LoginResponse> {
        return withContext(Dispatchers.IO){
            authLocalDataSource.saveIsLoggedIn(true)
            com.hrudhaykanth116.core.data.models.DataResult.Success(LoginResponse("Successfully logged in."))
        }
    }

    fun saveIsLoggedIn(isLoggedIn: Boolean){
        authLocalDataSource.saveIsLoggedIn(isLoggedIn)
    }

    fun isLoggedIn(): Boolean{
        return authLocalDataSource.isLoggedIn()
    }

}