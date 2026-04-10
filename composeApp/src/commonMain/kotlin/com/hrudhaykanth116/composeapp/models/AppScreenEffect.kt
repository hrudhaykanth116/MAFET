package com.hrudhaykanth116.composeapp.models

sealed class AppScreenEffect {
    data class OpenUrl(val url: String) : AppScreenEffect()
}
