package com.hrudhaykanth116.tv.domain.models

import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus

data class MyTv(
    val id: Int,
    val name: String,
    val lastWatchedSeason: Int?,
    val lastWatchedEpisode: Int?,
    val lastWatchedTime: Long?,
    val imgSource: String?,
    val status: WatchStatus = WatchStatus.WATCHING,
    val rating: Int? = null,
    val notes: String? = null,
)
