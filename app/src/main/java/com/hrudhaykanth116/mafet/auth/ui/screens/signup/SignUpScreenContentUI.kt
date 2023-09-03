package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpScreenCallbacks
import com.hrudhaykanth116.mafet.auth.ui.components.*

@Composable
fun SignUpScreenContentUI(
    modifier: Modifier = Modifier,
    state: SignUpFormState = SignUpFormState(),
    signUpScreenCallbacks: SignUpScreenCallbacks = SignUpScreenCallbacks(),
) {
    // TODO: Add progress bar based on state.isLoading.
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        state.userMessage?.let {
            val context = LocalContext.current
            Toast.makeText(context, it.getText(context), Toast.LENGTH_LONG).show()
            signUpScreenCallbacks.onUserMessageShown(it)
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
                signUpScreenCallbacks.onProfileClicked()
            }
            Text(text = "Set display picture")
        }

        EmailTextField(state.emailTextFieldValue, state.emailError, signUpScreenCallbacks.onEmailChanged)
        PasswordTextField(state.passwordTextFieldValue, state.passwordError, signUpScreenCallbacks.onPasswordChanged)
        ReEnterPasswordTextField(state, signUpScreenCallbacks.onReEnterPasswordChanged)
        UserNameTextField(state, signUpScreenCallbacks.onUserNameChanged)
        BioTextField(state, signUpScreenCallbacks.onBioChanged)
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(btnText = "Sign up") { signUpScreenCallbacks.onSubmit() }
    }
}

@MyPreview
@Composable
fun SignUpScreenContentUIPreview() {
    SignUpScreenContentUI(

    )
}