package com.hrudhaykanth116.media.ui.screens

import com.hrudhaykanth116.media.data.models.PhotoResponse

data class MediaScreenUIState(
    val photoList: List<PhotoResponse> = listOf(),
    val videoUrl: String? = null,
)