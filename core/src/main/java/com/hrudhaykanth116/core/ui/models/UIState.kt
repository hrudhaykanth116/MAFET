package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.data.models.UIText

sealed class UIState<out T>(open val contentState: T?) {

    open class LoadingUIState<T>(contentState: T? = null): UIState<T>(contentState)
    open class ErrorUIState<T>(val text: UIText, contentState: T? = null): UIState<T>(contentState)
    open class LoadedUIState<T>(override val contentState: T): UIState<T>(contentState)

}



