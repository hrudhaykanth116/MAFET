package com.hrudhaykanth116.auth.domain.models.login

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText

data class LoginScreenState(
    val loginEmail: TextFieldValue = TextFieldValue(),
    val emailError: UIText? = null,

    val loginPassword: TextFieldValue = TextFieldValue(),
    val passwordError: UIText? = null,

    val isLoggedIn: Boolean = false,
    val loginError: UIText? = null
)
