package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.mafet.R
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.utils.extensions.HandleEffect
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.utils.ui.ToastHelper

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {

    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }


    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    com.hrudhaykanth116.core.utils.extensions.HandleEffect(viewModel = viewModel) { effect ->
        when (effect) {
            is SignUpEffect.Success -> {
                com.hrudhaykanth116.core.utils.ui.ToastHelper.showSuccessToast(
                    context,
                    effect.message
                )
            }
            is SignUpEffect.Error -> {
                com.hrudhaykanth116.core.utils.ui.ToastHelper.showErrorToast(
                    context,
                    effect.message
                        ?: com.hrudhaykanth116.core.data.models.UIText.StringRes(R.string.something_went_wrong)
                )
            }
        }
    }

    com.hrudhaykanth116.core.ui.components.CenteredColumn {

        result.value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )

            com.hrudhaykanth116.core.ui.components.AppFormInputText(
                label = "Enter your email",
                inputValue = state.email,
                isError = !state.emailError?.getText().isNullOrBlank()
            ) { viewModel.processEvent(SignUpFormEvent.EmailChanged(it)) }

            if (!state.emailError?.getText().isNullOrBlank()) {
                Text(
                    text = state.emailError?.getText()!!
                )
            }


            com.hrudhaykanth116.core.ui.components.AppFormInputText(
                label = "Enter your password",
                inputValue = state.password,
                isError = !state.passwordError?.getText().isNullOrBlank()
            ) { viewModel.processEvent(SignUpFormEvent.PasswordChanged(it)) }

            if (!state.passwordError?.getText().isNullOrBlank()) {
                Text(
                    text = state.passwordError?.getText()!!
                )
            }

            com.hrudhaykanth116.core.ui.components.AppFormInputText(
                label = "ReEnter your password",
                inputValue = state.repeatedPassword,
                isError = !state.repeatedPasswordError?.getText().isNullOrBlank()
            ) { viewModel.processEvent(SignUpFormEvent.ReEnteredPasswordChanged(it)) }

            if (!state.repeatedPasswordError?.getText().isNullOrBlank()) {
                Text(
                    text = state.repeatedPasswordError?.getText()!!
                )
            }

            com.hrudhaykanth116.core.ui.components.AppFormButton(btnText = "Sign up") {
                viewModel.processEvent(SignUpFormEvent.Submit)
            }
        }

    }
}