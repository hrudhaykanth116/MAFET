package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.UIText

sealed class UIState<T>(
    open val contentState: T?,
    open val userMessage: UserMessage? = null
) {

    abstract fun copyUIState(contentState: T?, userMessage: UserMessage?): UIState<T>

    data class Loading<T>(
        override val contentState: T? = null,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {

        override fun copyUIState(contentState: T?, userMessage: UserMessage?): UIState<T> {
            return copy(contentState = contentState)
        }


    }

    data class Error<T>(
        val uiText: UIText? = null,
        override val contentState: T? = null,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {

        override fun copyUIState(contentState: T?, userMessage: UserMessage?): UIState<T> {
            return copy(contentState = contentState)
        }
    }

    data class Loaded<T>(
        override val contentState: T?,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {
        override fun copyUIState(contentState: T?, userMessage: UserMessage?): UIState<T> {
            return copy(contentState = contentState)
        }
    }

}



