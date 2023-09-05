package com.hrudhaykanth116.auth.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.auth.domain.models.login.LoginScreenCallBacks
import com.hrudhaykanth116.auth.domain.models.login.LoginScreenEvent
import com.hrudhaykanth116.core.theme.screenBackgroundModifier

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
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

    if (state.isLoggedIn) {
        // onLoggedIn("USERNAME")
    } else {
        LoginScreenUI(
            state = state,
            modifier = screenBackgroundModifier,
            loginScreenCallBacks = LoginScreenCallBacks(
                onSignUpBtnClicked = navigateToSignUpScreen,
                onEmailChanged = {
                    viewModel.processEvent(LoginScreenEvent.EmailChanged(it))
                },
                onPasswordChanged = {
                    viewModel.processEvent(LoginScreenEvent.PasswordChanged(it))
                },
                onLoginBtnClicked = {
                    // viewModel.processEvent(LoginScreenEvent.Login)
                    onLoggedIn()
                }
            ),
        )
    }


}

