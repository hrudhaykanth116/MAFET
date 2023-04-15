package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText

data class SignUpFormState(
    val name: TextFieldValue = TextFieldValue(),
    val nameError: com.hrudhaykanth116.core.data.models.UIText? = null,
    val email: TextFieldValue = TextFieldValue(),
    val emailError: com.hrudhaykanth116.core.data.models.UIText? = null,
    val password: TextFieldValue = TextFieldValue(),
    val passwordError: com.hrudhaykanth116.core.data.models.UIText? = null,
    val repeatedPassword: TextFieldValue = TextFieldValue(),
    val repeatedPasswordError: com.hrudhaykanth116.core.data.models.UIText? = null,
)