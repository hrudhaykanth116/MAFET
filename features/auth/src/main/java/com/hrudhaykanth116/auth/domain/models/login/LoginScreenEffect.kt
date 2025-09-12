package com.hrudhaykanth116.auth.domain.models.login

import com.hrudhaykanth116.core.domain.models.ErrorState

sealed class LoginScreenEffect {
    object LoggedIn : LoginScreenEffect()
    data class LogInFailed(val error: ErrorState) : LoginScreenEffect()
}