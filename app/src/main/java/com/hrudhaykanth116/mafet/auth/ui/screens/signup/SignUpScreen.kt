package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.mafet.R
import com.hrudhaykanth116.mafet.common.data.models.UIText
import com.hrudhaykanth116.mafet.common.extensions.HandleEffect
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.common.ui.components.AppFormInputText
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.common.utils.ui.ToastHelper

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    HandleEffect(viewModel = viewModel){ effect ->
        when (effect) {
            is SignUpEffect.Success -> {
                ToastHelper.showSuccessToast(context, effect.message)
            }
            is SignUpEffect.Error -> {
                ToastHelper.showErrorToast(context,
                    effect.message ?: UIText.StringRes(R.string.something_went_wrong)
                )
            }
        }
    }

    CenteredColumn {

        AppFormInputText(
            label = "Enter your email",
            inputValue = state.email,
            isError = !state.emailError?.getText().isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.EmailChanged(it)) }

        if (!state.emailError?.getText().isNullOrBlank()) {
            Text(
                text = state.emailError?.getText()!!
            )
        }


        AppFormInputText(
            label = "Enter your password",
            inputValue = state.password,
            isError = !state.passwordError?.getText().isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.PasswordChanged(it)) }

        if (!state.passwordError?.getText().isNullOrBlank()) {
            Text(
                text = state.passwordError?.getText()!!
            )
        }

        AppFormInputText(
            label = "ReEnter your password",
            inputValue = state.repeatedPassword,
            isError = !state.repeatedPasswordError?.getText().isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.ReEnteredPasswordChanged(it)) }

        if (!state.repeatedPasswordError?.getText().isNullOrBlank()) {
            Text(
                text = state.repeatedPasswordError?.getText()!!
            )
        }

        AppFormButton(btnText = "Sign up") {
            viewModel.processEvent(SignUpFormEvent.Submit)
        }
    }

}