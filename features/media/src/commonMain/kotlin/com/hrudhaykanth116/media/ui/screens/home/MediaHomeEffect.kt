package com.hrudhaykanth116.media.ui.screens.home

import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

sealed interface MediaHomeEffect {
    data class NavigateToDetail(val mediaId: Int, val type: MediaType) : MediaHomeEffect
    data object ShowFilters : MediaHomeEffect
    data object NavigateToSearch : MediaHomeEffect
}
