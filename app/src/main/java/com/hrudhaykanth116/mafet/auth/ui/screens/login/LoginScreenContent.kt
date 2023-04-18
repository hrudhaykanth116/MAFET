package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.auth.domain.models.login.LoginScreenState
import com.hrudhaykanth116.mafet.auth.ui.components.EmailTextField
import com.hrudhaykanth116.mafet.auth.ui.components.PasswordTextField

@Composable
fun LoginScreenContent(
    state: LoginScreenState,
    onSignUpBtnClicked: () -> Unit,
    onEmailChanged: (TextFieldValue) -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onLoginBtnClicked: () -> Unit
) {
    CenteredColumn {
        EmailTextField(state.loginEmail, state.emailError, onEmailChanged)
        PasswordTextField(state.loginPassword, state.passwordError, onPasswordChanged)
        AppFormButton("Login", onClick = onLoginBtnClicked)
        AppFormButton("Sign up", onClick = onSignUpBtnClicked)
    }
}