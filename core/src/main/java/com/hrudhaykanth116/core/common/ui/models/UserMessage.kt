package com.hrudhaykanth116.core.common.ui.models

import androidx.compose.runtime.Immutable
import com.hrudhaykanth116.core.data.models.UIText

@Immutable
sealed interface UserMessage{

    data class Success(val message: UIText): UserMessage

    data class Error(val message: UIText): UserMessage

    data class Warning(val message: UIText): UserMessage

}


fun UIText.toSuccessMessage(): UserMessage = UserMessage.Success(this)
fun UIText.toErrorMessage(): UserMessage.Error = UserMessage.Error(this)
fun UIText.toWarningMessage(): UserMessage = UserMessage.Warning(this)

fun String.toSuccessMessage(): UserMessage = UserMessage.Success(UIText.Text(this))
fun String.toErrorMessage(): UserMessage = UserMessage.Error(UIText.Text(this))
fun String.toWarningMessage(): UserMessage = UserMessage.Warning(UIText.Text(this))

fun Int.toSuccessMessage(): UserMessage = UserMessage.Success(UIText.StringRes(this))
fun Int.toErrorMessage(): UserMessage = UserMessage.Error(UIText.StringRes(this))
fun Int.toWarningMessage(): UserMessage = UserMessage.Warning(UIText.StringRes(this))
