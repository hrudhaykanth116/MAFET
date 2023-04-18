package com.hrudhaykanth116.mafet.auth.domain.models.login

sealed class LoginScreenEffect {
    object LoggedIn: LoginScreenEffect()
    data class LogInFailed(val error: com.hrudhaykanth116.core.data.models.DataResult.Error): LoginScreenEffect()
}