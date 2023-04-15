package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.utils.extensions.HandleEffect
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.utils.ui.ToastHelper

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: (String) -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    val state: LoginScreenState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    com.hrudhaykanth116.core.utils.extensions.HandleEffect(viewModel = viewModel) { effect ->
        when (effect) {
            LoginScreenEffect.LoggedIn -> {
                onLoggedIn(state.loginEmail.text)
            }
            is LoginScreenEffect.LogInFailed -> {
                val error = effect.error
                com.hrudhaykanth116.core.utils.ui.ToastHelper.showErrorToast(
                    context,
                    error.uiMessage
                )
            }
        }
    }

    com.hrudhaykanth116.core.ui.components.CenteredColumn {
        com.hrudhaykanth116.core.ui.components.AppFormInputText(state.loginEmail, label = "Email") {
            viewModel.processEvent(LoginScreenEvent.EmailChanged(it))
        }
        com.hrudhaykanth116.core.ui.components.AppFormInputText(
            state.loginPassword,
            label = "Password",
            inputType = com.hrudhaykanth116.core.ui.models.InputType.PwdInputType
        ) {
            viewModel.processEvent(LoginScreenEvent.PasswordChanged(it))
        }
        com.hrudhaykanth116.core.ui.components.AppFormButton("Login") {
            viewModel.processEvent(LoginScreenEvent.Login)
        }
        com.hrudhaykanth116.core.ui.components.AppFormButton("Sign up") {
            navigateToSignUpScreen()
        }
    }
}