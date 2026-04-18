package com.hrudhaykanth116.tv.ui.screens

import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData

data class TvShowsScreenUIState(
    val isLoading: Boolean = true,
    val tvShows: List<TvShowData> = emptyList(),
    val error: String? = null
)
