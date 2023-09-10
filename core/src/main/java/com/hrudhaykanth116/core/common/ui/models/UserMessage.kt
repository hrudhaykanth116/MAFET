package com.hrudhaykanth116.core.common.ui.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface UserMessage{

    data class Success(val message: UIText): UserMessage

    data class Error(val message: UIText): UserMessage

    data class Warning(val message: UIText): UserMessage

}
