package com.hrudhaykanth116.mafet.auth.ui.screens.login

import com.hrudhaykanth116.mafet.common.data.models.DataResult

sealed class LoginScreenEffect {
    object LoggedIn: LoginScreenEffect()
    data class LogInFailed(val error: DataResult.Error): LoginScreenEffect()
}