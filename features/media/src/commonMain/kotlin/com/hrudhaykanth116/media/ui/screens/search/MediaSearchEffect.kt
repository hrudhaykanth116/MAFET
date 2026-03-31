package com.hrudhaykanth116.media.ui.screens.search

import com.hrudhaykanth116.media.domain.models.MediaType

sealed interface MediaSearchEffect {
    data object NavigateBack : MediaSearchEffect
    data class NavigateToDetail(val mediaId: Int, val type: MediaType) : MediaSearchEffect
}
