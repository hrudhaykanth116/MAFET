package com.hrudhaykanth116.mafet.auth.domain.models

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText

data class LoginScreenState(
    val loginEmail: TextFieldValue = TextFieldValue(),
    val loginPassword: TextFieldValue = TextFieldValue(),
    val isLoggedIn: Boolean = false,
    val loginError: UIText? = null
)
