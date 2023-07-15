package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.mafet.auth.ui.components.*

@MyPreview
@Composable
fun SignUpScreenContent(
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

        EmailTextField(state.emailTextFieldValue, state.emailError, onEmailChanged)
        PasswordTextField(state.passwordTextFieldValue, state.passwordError, onPasswordChanged)
        ReEnterPasswordTextField(state, onReEnterPasswordChanged)
        UserNameTextField(state, onUserNameChanged)
        BioTextField(state, onBioChanged)
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(btnText = "Sign up") { onSubmit() }
    }
}