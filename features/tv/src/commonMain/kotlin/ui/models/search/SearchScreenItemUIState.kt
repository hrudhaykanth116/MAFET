package com.hrudhaykanth116.tv.ui.models.search

import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class SearchScreenItemUIState(
    val id: Int,
    val name: UIText,
    val image: ImageHolder?,
    val isMyTvList: Boolean,
    val overview: String = "",
    val rating: Double = 0.0,
    val firstAirDate: String? = null,
)
