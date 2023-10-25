package com.hrudhaykanth116.tv.domaintemp.models

data class MyTvDomainModel(
    val id: Int,
    val name: String,
    val lastWatchedSeason: Int? = null,
    val lastWatchedEpisode: Int? = null,
    val lastWatchedTime: Long? = null,
    val imgSource: String?,
)