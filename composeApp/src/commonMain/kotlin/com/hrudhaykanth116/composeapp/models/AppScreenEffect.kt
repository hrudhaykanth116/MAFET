package com.hrudhaykanth116.composeapp.models

sealed class AppScreenEffect {
    // TODO: perform actions based on the action string, e.g. navigate, open URL, etc.
    data class HandleDialogAction(val action: String) : AppScreenEffect()
}
