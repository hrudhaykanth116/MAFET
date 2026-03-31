package com.hrudhaykanth116.core.ui.models

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
sealed interface UserMessage{

    data class Success(val message: UIText): UserMessage

    data class Error(val message: UIText): UserMessage

    data class Warning(val message: UIText): UserMessage

    companion object {
        const val ACTION_LABEL_SUCCESS = "success"
        const val ACTION_LABEL_ERROR = "error"
        const val ACTION_LABEL_WARNING = "warning"
    }

}


fun UIText.toSuccessMessage(): UserMessage = UserMessage.Success(this)
fun UIText.toErrorMessage(): UserMessage.Error = UserMessage.Error(this)
fun UIText.toWarningMessage(): UserMessage = UserMessage.Warning(this)

fun String.toSuccessMessage(): UserMessage = UserMessage.Success(UIText.Text(this))
fun String.toErrorMessage(): UserMessage = UserMessage.Error(UIText.Text(this))
fun String.toWarningMessage(): UserMessage = UserMessage.Warning(UIText.Text(this))

fun StringResource.toSuccessMessage(): UserMessage = UserMessage.Success(UIText.StringRes(this))
fun StringResource.toErrorMessage(): UserMessage = UserMessage.Error(UIText.StringRes(this))
fun StringResource.toWarningMessage(): UserMessage = UserMessage.Warning(UIText.StringRes(this))