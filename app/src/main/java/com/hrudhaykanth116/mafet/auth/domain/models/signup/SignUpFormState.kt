package com.hrudhaykanth116.mafet.auth.domain.models.signup

import android.graphics.Bitmap
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidateEmailUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidatePasswordUseCase

data class SignUpFormState(
    val imgBitmap: Bitmap? = null,

    val emailTextFieldValue: TextFieldValue = TextFieldValue(),
    val emailError: UIText? = null,

    val passwordTextFieldValue: TextFieldValue = TextFieldValue(),
    val passwordError: UIText? = null,

    val repeatedPassword: TextFieldValue = TextFieldValue(),
    val repeatedPasswordError: UIText? = null,

    val userName: TextFieldValue = TextFieldValue(),
    val userNameError: UIText? = null,

    val bio: TextFieldValue = TextFieldValue(),
    val bioError: UIText? = null,

    val userMessage: UIText? = null,
    val isSignedUp: Boolean = false,

    val isLoading: Boolean = false,
) {

    fun getStateAfterValidation(
        validateEmailUseCase: ValidateEmailUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase
    ): SignUpFormState {
        return copy(
            emailError = validateEmailUseCase(emailTextFieldValue.text),
            passwordError = validatePasswordUseCase(passwordTextFieldValue.text),
            repeatedPasswordError = if (passwordTextFieldValue.text != repeatedPassword.text) UIText.Text(
                "Passwords do not match"
            ) else null,
            userNameError = if (userName.text.isBlank()) UIText.Text("User name cannot be empty") else null,
            bioError = if (bio.text.isBlank()) UIText.Text("Bio cannot be empty") else null,
        )
    }

    fun containsError(): Boolean {
        return emailError != null
                || passwordError != null
                || repeatedPasswordError != null
                || userNameError != null
                || bioError != null
    }

}