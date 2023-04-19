package com.hrudhaykanth116.core.ui.models

import com.hrudhaykanth116.core.data.models.UIText

sealed interface UIState<out T> {

    open class LoadingUIState: UIState<Nothing>
    open class ErrorUIState(val text: UIText): UIState<Nothing>
    open class LoadedUIState<T>(val data: T): UIState<T>

}



