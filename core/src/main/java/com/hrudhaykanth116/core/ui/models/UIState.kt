package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.domain.models.ErrorState

sealed class UIState<out T>(open val contentState: T?) {

    open class Loading<T>(contentState: T? = null): UIState<T>(contentState)
    open class Error<T>(val text: String, contentState: T? = null): UIState<T>(contentState)
    open class Loaded<T>(override val contentState: T): UIState<T>(contentState)

}



