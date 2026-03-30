package com.hrudhaykanth116.tv.domain.models

data class Season(
    val id: Int,
    val name: String,
    val overview: String,
    val airDate: String?,
    val episodeCount: Int,
    val posterPath: String?,
    val seasonNumber: Int
)
