package com.hrudhaykanth116.mafet.auth.ui.states

import androidx.compose.ui.text.input.TextFieldValue

data class SignUpFormState(
    val name: TextFieldValue = TextFieldValue(),
    val nameError: String? = null,
    val email: TextFieldValue = TextFieldValue(),
    val emailError: String? = null,
    val password: TextFieldValue = TextFieldValue(),
    val passwordError: String? = null,
    val repeatedPassword: TextFieldValue = TextFieldValue(),
    val repeatedPasswordError: String? = null,
)