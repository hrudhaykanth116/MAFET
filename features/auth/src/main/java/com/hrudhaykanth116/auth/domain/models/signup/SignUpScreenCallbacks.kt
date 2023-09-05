package com.hrudhaykanth116.auth.domain.models.signup

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText

data class SignUpScreenCallbacks(
    val onProfileClicked: () -> Unit = {},
    val onEmailChanged: (TextFieldValue) -> Unit = {},
    val onPasswordChanged: (TextFieldValue) -> Unit = {},
    val onReEnterPasswordChanged: (TextFieldValue) -> Unit = {},
    val onUserNameChanged: (TextFieldValue) -> Unit = {},
    val onBioChanged: (TextFieldValue) -> Unit = {},
    val onUserMessageShown: (UIText) -> Unit = {},
    val onSubmit: () -> Unit = {},
)
