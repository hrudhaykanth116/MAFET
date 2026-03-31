package com.hrudhaykanth116.media.ui.screens.search

import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType

data class MediaSearchUIState(
    val query: String = "",
    val mediaType: MediaType = MediaType.PHOTOS,
    val filters: FilterState = FilterState(),
    val items: List<MediaItem> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val trendingSearches: List<String> = listOf(
        "Nature", "Architecture", "Food", "Travel",
        "Sunset", "Minimal", "Portrait", "City"
    ),
    val isSearching: Boolean = false,
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 1,
    val canLoadMore: Boolean = true,
    val showSuggestions: Boolean = true
)
