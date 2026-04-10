package com.hrudhaykanth116.composeapp.models

sealed class AppScreenEvent {
    /** Fired by any gate button click, or by tapping outside a dismissable gate. */
    data class GateButtonAction(val action: String) : AppScreenEvent()
}
