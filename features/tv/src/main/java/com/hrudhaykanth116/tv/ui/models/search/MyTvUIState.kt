package com.hrudhaykanth116.tv.ui.models.search

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class MyTvUIState(
    val id: String,
    val name: UIText,
    val currentEpisode: UIText,
    val lastWatched: UIText,
    val imgSource: ImageHolder,
)