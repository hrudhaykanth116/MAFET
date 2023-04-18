package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.mafet.auth.domain.models.login.LoginScreenEvent
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormEvent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: (String) -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    // com.hrudhaykanth116.core.utils.extensions.HandleEffect(viewModel = viewModel) { effect ->
    //     when (effect) {
    //         LoginScreenEffect.LoggedIn -> {
    //             onLoggedIn(state.loginEmail.text)
    //         }
    //         is LoginScreenEffect.LogInFailed -> {
    //             val error = effect.error
    //             com.hrudhaykanth116.core.utils.ui.ToastHelper.showErrorToast(
    //                 context,
    //                 error.uiMessage
    //             )
    //         }
    //     }
    // }

    LoginScreenContent(
        state,
        navigateToSignUpScreen,
        onEmailChanged = {
            viewModel.processEvent(LoginScreenEvent.EmailChanged(it))
        },
        onPasswordChanged = {
            viewModel.processEvent(LoginScreenEvent.PasswordChanged(it))
        },
        onLoginBtnClicked = {
            viewModel.processEvent(LoginScreenEvent.Login)
        }
    )
}

