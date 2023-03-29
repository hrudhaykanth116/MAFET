package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.compose.ui.text.input.TextFieldValue

sealed class SignUpFormEvent {
    data class EmailChanged(val email: TextFieldValue): SignUpFormEvent()
    data class PasswordChanged(val password: TextFieldValue): SignUpFormEvent()
    data class ReEnteredPasswordChanged(val password: TextFieldValue): SignUpFormEvent()
    object Submit: SignUpFormEvent()
}