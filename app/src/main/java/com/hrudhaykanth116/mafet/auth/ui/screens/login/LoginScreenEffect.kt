package com.hrudhaykanth116.mafet.auth.ui.screens.login

import com.hrudhaykanth116.core.data.models.DataResult

sealed class LoginScreenEffect {
    object LoggedIn: LoginScreenEffect()
    data class LogInFailed(val error: com.hrudhaykanth116.core.data.models.DataResult.Error): LoginScreenEffect()
}