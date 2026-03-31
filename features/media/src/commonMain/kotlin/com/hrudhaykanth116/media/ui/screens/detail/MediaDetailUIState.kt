package com.hrudhaykanth116.media.ui.screens.detail

import com.hrudhaykanth116.media.domain.models.MediaItem

data class MediaDetailUIState(
    val mediaItem: MediaItem? = null,
    val isDownloading: Boolean = false
)
