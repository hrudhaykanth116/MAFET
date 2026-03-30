package com.hrudhaykanth116.tv.data.datasources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieData(
    var id: Int?,
    var adult: Boolean?,
    @SerialName("backdrop_path")
    var backdropPath: String?,
    @SerialName("genre_ids")
    var genreIds: List<Int>,
    @SerialName("original_language")
    var originalLanguage: String?,
    @SerialName("original_title")
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double,
    @SerialName("poster_path")
    var posterPath: String?,
    @SerialName("release_date")
    var releaseDate: String?,
    var title: String?,
    var video: Boolean?,
    @SerialName("vote_average")
    var voteAverage: Double?,
    @SerialName("vote_count")
    var voteCount: Int?
)