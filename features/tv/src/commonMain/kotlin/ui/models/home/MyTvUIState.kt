package com.hrudhaykanth116.tv.ui.models.home

import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus

data class MyTvUIState(
    val id: Int,
    val name: UIText,
    val lastWatchedSeason: Int?,
    val lastWatchedEpisode: Int?,
    val lastWatchedSeasonEpisode: UIText,
    val lastWatchedTimeUIText: UIText,
    val lastWatchedTime: Long?,
    val imgSource: ImageHolder?,
    val status: WatchStatus = WatchStatus.WATCHING,
    val rating: Int? = null,
    val notes: String? = null,
)