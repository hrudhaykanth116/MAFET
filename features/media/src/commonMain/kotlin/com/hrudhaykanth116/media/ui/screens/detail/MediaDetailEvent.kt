package com.hrudhaykanth116.media.ui.screens.detail

sealed interface MediaDetailEvent {
    data object OnBackClick : MediaDetailEvent
    data object OnShareClick : MediaDetailEvent
    data class OnDownloadClick(val quality: DownloadQuality) : MediaDetailEvent
    data object OnPhotographerClick : MediaDetailEvent
}

enum class DownloadQuality {
    ORIGINAL,
    MEDIUM,
    SMALL
}
