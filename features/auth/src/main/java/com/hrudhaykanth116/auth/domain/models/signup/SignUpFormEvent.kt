package com.hrudhaykanth116.auth.domain.models.signup

import android.graphics.Bitmap
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText

sealed interface SignUpFormEvent {
    data class ProfileImageChanged(val imgBitmap: Bitmap?): SignUpFormEvent
    data class EmailChanged(val email: TextFieldValue): SignUpFormEvent
    data class PasswordChanged(val password: TextFieldValue): SignUpFormEvent
    data class ReEnteredPasswordChanged(val password: TextFieldValue): SignUpFormEvent
    data class UserNameChanged(val userName: TextFieldValue): SignUpFormEvent
    data class BioChanged(val bio: TextFieldValue): SignUpFormEvent
    data class UserMessageShown(val message: UIText): SignUpFormEvent
    object Submit: SignUpFormEvent
}