package com.hrudhaykanth116.media.ui.screens.home

import com.hrudhaykanth116.media.domain.models.FilterState
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.models.OrientationType

data class MediaHomeUIState(
    val mediaType: MediaType = MediaType.PHOTOS,
    val orientation: OrientationType = OrientationType.ALL,
    val filters: FilterState = FilterState(),
    val items: List<MediaItem> = emptyList(),
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 1,
    val canLoadMore: Boolean = true
)
