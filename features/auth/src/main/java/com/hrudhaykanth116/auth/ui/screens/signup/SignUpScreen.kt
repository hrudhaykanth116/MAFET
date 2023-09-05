package com.hrudhaykanth116.auth.ui.screens.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.auth.domain.models.signup.SignUpEffect
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormEvent
import com.hrudhaykanth116.auth.domain.models.signup.SignUpScreenCallbacks
import com.hrudhaykanth116.core.common.utils.extensions.HandleEffect
import com.hrudhaykanth116.core.theme.screenBackgroundModifier

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

    SignUpScreenContentUI(
        modifier = Modifier
            .fillMaxSize()
            .then(screenBackgroundModifier),
        state = state,
        signUpScreenCallbacks = SignUpScreenCallbacks(
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
    )
}