package com.hrudhaykanth116.tv.domain.models

data class Episode(
    val id: Int,
    val name: String,
    val overview: String,
    val airDate: String?,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val productionCode: String
)
