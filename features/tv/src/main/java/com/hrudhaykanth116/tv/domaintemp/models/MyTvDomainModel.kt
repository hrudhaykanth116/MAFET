package com.hrudhaykanth116.tv.domaintemp.models

data class MyTvDomainModel(
    val id: Int,
    val name: String,
    val currentEpisode: String,
    val lastWatched: String,
    val imgSource: String,
)