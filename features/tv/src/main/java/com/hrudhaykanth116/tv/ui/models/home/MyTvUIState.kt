package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

data class MyTvUIState(
    val id: Int,
    val name: UIText,
    val lastWatchedSeason: Int?,
    val lastWatchedEpisode: Int?,
    val lastWatchedSeasonEpisode: UIText,
    val lastWatchedTimeUIText: UIText,
    val lastWatchedTime: Long?,
    val imgSource: ImageHolder?,
)