package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.common.utils.extensions.HandleEffect
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpEffect
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormEvent

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignedIn: () -> Unit = {},
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        viewModel.processEvent(SignUpFormEvent.ProfileImageChanged(it))
    }

    if (state.isSignedUp) {
        onSignedIn()
    }

    HandleEffect(viewModel = viewModel) { effect ->
        when (effect) {
            is SignUpEffect.Success -> {
                // ToastHelper.showSuccessToast(context, effect.message)
            }
            is SignUpEffect.Error -> {
                // ToastHelper.showErrorToast(context,
                //     effect.message ?: UIText.StringRes(R.string.something_went_wrong)
                // )
            }
        }
    }


    Column(
        modifier = modifier,
    ) {
        AppToolbar(
            modifier = modifier.fillMaxWidth(),
            text = "Register",
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            SignUpScreenContent(
                modifier = Modifier.fillMaxSize(),
                state,
                onProfileClicked = {
                    launcher.launch(null)
                },
                onEmailChanged = {
                    viewModel.processEvent(SignUpFormEvent.EmailChanged(it))
                },
                onPasswordChanged = {
                    viewModel.processEvent(SignUpFormEvent.PasswordChanged(it))
                },
                onReEnterPasswordChanged = {
                    viewModel.processEvent(SignUpFormEvent.ReEnteredPasswordChanged(it))
                },
                onUserNameChanged = {
                    viewModel.processEvent(SignUpFormEvent.UserNameChanged(it))
                },
                onBioChanged = {
                    viewModel.processEvent(SignUpFormEvent.BioChanged(it))
                },
                onUserMessageShown = {
                    viewModel.processEvent(SignUpFormEvent.UserMessageShown(it))
                },
                onSubmit = {
                    viewModel.processEvent(SignUpFormEvent.Submit)
                }
            )
            if (state.isLoading) CircularProgressIndicator()
        }
    }
}