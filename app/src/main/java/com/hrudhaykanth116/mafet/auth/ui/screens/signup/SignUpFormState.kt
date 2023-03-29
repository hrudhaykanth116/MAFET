package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.mafet.common.data.models.UIText

data class SignUpFormState(
    val name: TextFieldValue = TextFieldValue(),
    val nameError: UIText? = null,
    val email: TextFieldValue = TextFieldValue(),
    val emailError: UIText? = null,
    val password: TextFieldValue = TextFieldValue(),
    val passwordError: UIText? = null,
    val repeatedPassword: TextFieldValue = TextFieldValue(),
    val repeatedPasswordError: UIText? = null,
)