package com.hrudhaykanth116.tv.domain.models

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String?,
    val popularity: Double,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalName: String,
    val originCountry: List<String>
)
