package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class MyTvUIState(
    val id: Int,
    val name: UIText,
    val lastWatchedSeason: Int? = null,
    val lastWatchedEpisode: Int? = null,
    val lastWatchedSeasonEpisode: UIText,
    val lastWatchedTime: UIText,
    val imgSource: ImageHolder,
)