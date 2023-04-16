package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.utils.compose.MyPreview
import com.hrudhaykanth116.core.utils.extensions.HandleEffect

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

@MyPreview
@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    state: SignUpFormState = SignUpFormState(),
    onProfileClicked: () -> Unit = {},
    onEmailChanged: (TextFieldValue) -> Unit = {},
    onPasswordChanged: (TextFieldValue) -> Unit = {},
    onReEnterPasswordChanged: (TextFieldValue) -> Unit = {},
    onUserNameChanged: (TextFieldValue) -> Unit = {},
    onBioChanged: (TextFieldValue) -> Unit = {},
    onUserMessageShown: (UIText) -> Unit = {},
    onSubmit: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        state.userMessage?.let {
            val context = LocalContext.current
            Toast.makeText(context, it.getText(context), Toast.LENGTH_LONG).show()
            onUserMessageShown(it)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircularImage(
                modifier = Modifier.size(100.dp),
                image = state.imgBitmap
                // ?: R.drawable.profile_icon,
            ) {
                onProfileClicked()
            }
            Text(text = "Set display picture")
        }

        EmailTextField(state, onEmailChanged)
        PasswordTextField(state, onPasswordChanged)
        ReEnterPasswordTextField(state, onReEnterPasswordChanged)
        UserNameTextField(state, onUserNameChanged)
        BioTextField(state, onBioChanged)
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(btnText = "Sign up") { onSubmit() }
    }
}

@Composable
private fun UserNameTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        TextFieldData(
            hint = "Username",
            inputType = InputType.RegularInputType,
            inputValue = state.userName,
            error = state.userNameError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}

@Composable
private fun BioTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        TextFieldData(
            hint = "A short bio",
            inputType = InputType.RegularInputType,
            inputValue = state.bio,
            error = state.bioError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}

@Composable
private fun ReEnterPasswordTextField(
    state: SignUpFormState,
    onReEnterPasswordChange: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        TextFieldData(
            hint = "ReEnter your password",
            inputType = InputType.PwdInputType,
            inputValue = state.repeatedPassword,
            error = state.repeatedPasswordError?.getText()
        )
    ) { onReEnterPasswordChange(it) }
}

@Composable
private fun PasswordTextField(
    state: SignUpFormState,
    onPasswordChanged: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        TextFieldData(
            hint = "Enter your password",
            inputType = InputType.PwdInputType,
            inputValue = state.passwordTextFieldValue,
            error = state.passwordError?.getText()
        )
    ) { onPasswordChanged(it) }
}

@Composable
private fun EmailTextField(
    state: SignUpFormState,
    onEmailChanged: (TextFieldValue) -> Unit
) {
    AppFormInputText(
        TextFieldData(
            hint = "Enter your email",
            inputType = InputType.EmailInputType,
            inputValue = state.emailTextFieldValue,
            error = state.emailError?.getText()
        )
    ) { onEmailChanged(it) }
}