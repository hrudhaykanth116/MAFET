package com.hrudhaykanth116.mafet.auth.domain.models.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginScreenCallBacks(
    val onSignUpBtnClicked: () -> Unit = {},
    val onEmailChanged: (TextFieldValue) -> Unit = {},
    val onPasswordChanged: (TextFieldValue) -> Unit = {},
    val onLoginBtnClicked: () -> Unit = {},
)