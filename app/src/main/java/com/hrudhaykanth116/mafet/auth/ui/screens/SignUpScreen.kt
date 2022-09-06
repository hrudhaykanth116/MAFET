package com.hrudhaykanth116.mafet.auth.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.mafet.auth.ui.SignUpViewModel
import com.hrudhaykanth116.mafet.auth.ui.effects.SignUpEffect
import com.hrudhaykanth116.mafet.auth.ui.events.SignUpFormEvent
import com.hrudhaykanth116.mafet.common.extensions.HandleEffect
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.common.ui.components.AppFormInputText
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.common.utils.ui.ToastHelper

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel = viewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    HandleEffect(viewModel = viewModel){ effect ->
        when (effect) {
            is SignUpEffect.Success -> {
                ToastHelper.showSuccessToast(context, effect.message!!)
            }
            is SignUpEffect.Error -> {
                ToastHelper.showErrorToast(context, effect.message!!)
            }
        }
    }



    CenteredColumn {

        AppFormInputText(
            label = "Enter your email",
            inputValue = state.email,
            isError = !state.emailError.isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.EmailChanged(it)) }

        if (!state.emailError.isNullOrBlank()) {
            Text(
                text = state.emailError!!
            )
        }


        AppFormInputText(
            label = "Enter your password",
            inputValue = state.password,
            isError = !state.passwordError.isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.PasswordChanged(it)) }

        if (!state.passwordError.isNullOrBlank()) {
            Text(
                text = state.passwordError!!
            )
        }

        AppFormInputText(
            label = "ReEnter your password",
            inputValue = state.repeatedPassword,
            isError = !state.repeatedPasswordError.isNullOrBlank()
        ) { viewModel.processEvent(SignUpFormEvent.ReEnteredPasswordChanged(it)) }

        if (!state.repeatedPasswordError.isNullOrBlank()) {
            Text(
                text = state.repeatedPasswordError!!
            )
        }

        AppFormButton(btnText = "Sign up") {
            viewModel.processEvent(SignUpFormEvent.Submit)
        }
    }

}