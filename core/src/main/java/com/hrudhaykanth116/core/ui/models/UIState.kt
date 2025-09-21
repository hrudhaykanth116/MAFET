package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.domain.models.ErrorState

/**
 * During loading/error states, this content state may or may not have some data state.
 * If non null contentState, just show toast. If null, show empty static data/error ui at the center.
 */
sealed class UIState<T>(
    open val contentState: T?,
    // open val userMessage: UserMessage? = null
) {

    data class Loading<T>(
        override val contentState: T? = null,
    ) : UIState<T>(contentState)

    data class Error<T>(
        val errorState: ErrorState,
        override val contentState: T? = null,
    ) : UIState<T>(contentState)

    data class Idle<T>(
        override val contentState: T? = null,
        val userMessage: UserMessage? = null
    ) : UIState<T>(contentState)

}



