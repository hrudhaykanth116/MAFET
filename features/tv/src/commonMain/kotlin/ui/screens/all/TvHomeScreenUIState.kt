package com.hrudhaykanth116.tv.ui.screens.all

import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.tv.domain.models.TvCategory

data class TvHomeScreenUIState(
    val categories: List<TvShowCategoryUi> = emptyList()
)

data class TvShowCategoryUi(
    val category: TvCategory,
    val title: String,
    val shows: List<TvShowUi>
)

data class TvShowUi(
    val id: Int,
    val name: String,
    val posterImage: ImageHolder,
    val rating: Double
)
