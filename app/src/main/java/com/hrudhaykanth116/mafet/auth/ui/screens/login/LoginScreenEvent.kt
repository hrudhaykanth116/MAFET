package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.mafet.auth.ui.screens.signup.SignUpFormEvent

sealed class LoginScreenEvent {
    object Login
        // val userName: String?,
        // val pwd: String?,
     : LoginScreenEvent()
    data class EmailChanged(val email: TextFieldValue): LoginScreenEvent()
    data class PasswordChanged(val pwd: TextFieldValue): LoginScreenEvent()
}