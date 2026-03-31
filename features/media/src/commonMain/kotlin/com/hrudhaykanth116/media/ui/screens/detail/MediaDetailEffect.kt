package com.hrudhaykanth116.media.ui.screens.detail

sealed interface MediaDetailEffect {
    data object NavigateBack : MediaDetailEffect
    data class ShareMedia(val url: String, val photographer: String) : MediaDetailEffect
    data class OpenUrl(val url: String) : MediaDetailEffect
}
