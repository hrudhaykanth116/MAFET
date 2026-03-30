package com.hrudhaykanth116.auth.domain.models.login

import com.hrudhaykanth116.core.domain.result.DomainError

sealed class LoginScreenEffect {
    object LoggedIn : LoginScreenEffect()
    data class LogInFailed(val error: DomainError) : LoginScreenEffect()
}