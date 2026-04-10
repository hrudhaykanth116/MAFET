package com.hrudhaykanth116.tv.ui.screens.details

import com.hrudhaykanth116.core.ui.models.ImageHolder

data class TvDetailsScreenUIState(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropImage: ImageHolder?,
    val dateRange: String,
    val rating: String,
    val genres: List<String>,
    val networks: List<NetworkUIState>,
)

data class NetworkUIState(
    val name: String,
    val logo: ImageHolder?,
)
