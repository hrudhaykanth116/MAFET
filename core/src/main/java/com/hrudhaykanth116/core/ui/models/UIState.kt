package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.UIText

/**
 * During loading/error states, this content state may or may not have some data state.
 * If non null contentState, just show toast. If null, show empty static data/error ui at the center.
 */
sealed class UIState<T>(
    open val contentState: T?,
    open val userMessage: UserMessage? = null
) {

    abstract fun copyUIState(
        newContentState: T? = contentState,
        newUserMessage: UserMessage? = userMessage
    ): UIState<T>

    data class Loading<T>(
        override val contentState: T? = null,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {

        override fun copyUIState(newContentState: T?, newUserMessage: UserMessage?): UIState<T> {
            return copy(contentState = newContentState, userMessage = newUserMessage)
        }

    }

    data class Error<T>(
        val uiText: UIText? = null,
        override val contentState: T? = null,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {

        override fun copyUIState(newContentState: T?, newUserMessage: UserMessage?): UIState<T> {
            return copy(uiText = uiText, contentState = newContentState, userMessage = newUserMessage)
        }
    }

    data class Idle<T>(
        override val contentState: T? = null,
        override val userMessage: UserMessage? = null
    ) : UIState<T>(contentState) {

        override fun copyUIState(newContentState: T?, newUserMessage: UserMessage?): UIState<T> {
            return copy(contentState = newContentState, userMessage = newUserMessage)
        }

    }

}



