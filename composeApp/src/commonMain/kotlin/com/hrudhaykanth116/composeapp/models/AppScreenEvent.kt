package com.hrudhaykanth116.composeapp.models

sealed class AppScreenEvent {
    object DismissDialog : AppScreenEvent()
    data class DialogButtonClicked(val action: String) : AppScreenEvent()
}
