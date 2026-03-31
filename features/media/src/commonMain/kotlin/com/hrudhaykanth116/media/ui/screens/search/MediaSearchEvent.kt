package com.hrudhaykanth116.media.ui.screens.search

import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

sealed interface MediaSearchEvent {
    data class OnQueryChange(val query: String) : MediaSearchEvent
    data class OnSearch(val query: String) : MediaSearchEvent
    data class OnRecentSearchClick(val query: String) : MediaSearchEvent
    data class OnTrendingSearchClick(val query: String) : MediaSearchEvent
    data object OnClearQuery : MediaSearchEvent
    data object OnBackClick : MediaSearchEvent
    data class ToggleMediaType(val type: MediaType) : MediaSearchEvent
    data class ApplyFilters(val filters: FilterState) : MediaSearchEvent
    data class OnMediaItemClick(val item: MediaItem) : MediaSearchEvent
    data object LoadNextPage : MediaSearchEvent
}
