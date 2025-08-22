package com.hrudhaykanth116.tv.ui.screens.all

data class TvHomeScreenUIState(
    val categories: List<TvShowCategoryUi> = emptyList()
)

data class TvShowCategoryUi(
    val title: String,
    val shows: List<TvShowUi>
)

data class TvShowUi(
    val id: Int,
    val name: String,
    val posterUrl: String,
    val rating: Double
)
