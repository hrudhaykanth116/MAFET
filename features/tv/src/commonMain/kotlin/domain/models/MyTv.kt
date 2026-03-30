package com.hrudhaykanth116.tv.domain.models

data class MyTv(
    val id: Int,
    val name: String,
    val lastWatchedSeason: Int?,
    val lastWatchedEpisode: Int?,
    val lastWatchedTime: Long?,
    val imgSource: String?
)
