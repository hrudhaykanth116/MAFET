package com.hrudhaykanth116.core.common.ui.models

import androidx.compose.runtime.Immutable
import com.hrudhaykanth116.core.data.models.UIText

@Immutable
sealed interface UserMessage{

    data class Success(val message: UIText): UserMessage

    data class Error(val message: UIText): UserMessage

    data class Warning(val message: UIText): UserMessage

}
