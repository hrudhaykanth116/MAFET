package com.hrudhaykanth116.media.ui.screens.home

import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.models.OrientationType

sealed interface MediaHomeEvent {
    data object LoadNextPage : MediaHomeEvent
    data object Refresh : MediaHomeEvent
    data class ToggleMediaType(val type: MediaType) : MediaHomeEvent
    data class FilterOrientation(val orientation: OrientationType) : MediaHomeEvent
    data class ApplyFilters(val filters: FilterState) : MediaHomeEvent
    data class OnMediaItemClick(val item: MediaItem) : MediaHomeEvent
    data object OpenFilters : MediaHomeEvent
    data object OpenSearch : MediaHomeEvent
}
