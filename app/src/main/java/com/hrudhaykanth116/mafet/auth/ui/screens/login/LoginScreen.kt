package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.mafet.common.extensions.HandleEffect
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.common.ui.components.AppFormInputText
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.common.ui.models.InputType
import com.hrudhaykanth116.mafet.common.utils.ui.ToastHelper

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: (String) -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    val state: LoginScreenState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    HandleEffect(viewModel = viewModel) { effect ->
        when (effect) {
            LoginScreenEffect.LoggedIn -> {
                onLoggedIn(state.loginEmail.text)
            }
            is LoginScreenEffect.LogInFailed -> {
                val error = effect.error
                ToastHelper.showErrorToast(
                    context,
                    error.uiMessage
                )
            }
        }
    }

    CenteredColumn {
        AppFormInputText(state.loginEmail, label = "Email") {
            viewModel.processEvent(LoginScreenEvent.EmailChanged(it))
        }
        AppFormInputText(
            state.loginPassword,
            label = "Password",
            inputType = InputType.PwdInputType
        ) {
            viewModel.processEvent(LoginScreenEvent.PasswordChanged(it))
        }
        AppFormButton("Login") {
            viewModel.processEvent(LoginScreenEvent.Login)
        }
        AppFormButton("Sign up") {
            navigateToSignUpScreen()
        }
    }
}