package com.hrudhaykanth116.auth.domain.models.login

import androidx.compose.ui.text.input.TextFieldValue

sealed class LoginScreenEvent {
    object Login
        // val userName: String?,
        // val pwd: String?,
     : LoginScreenEvent()
    data class EmailChanged(val email: TextFieldValue): LoginScreenEvent()
    data class PasswordChanged(val pwd: TextFieldValue): LoginScreenEvent()
}