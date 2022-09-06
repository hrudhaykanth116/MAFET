package com.hrudhaykanth116.mafet.auth.data.data_sources.local

import com.hrudhaykanth116.mafet.auth.data.local.shared_preferences.AuthPreffs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(
    private val authPreffs: AuthPreffs
) {

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean){
        authPreffs.isLoggedIn = isLoggedIn
    }

}