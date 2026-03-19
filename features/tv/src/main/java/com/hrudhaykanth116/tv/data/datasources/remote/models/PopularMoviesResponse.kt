package com.hrudhaykanth116.tv.data.datasources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
    var page: Int,
    @SerialName("total_results")
    var totalResults: Int,
    @SerialName("total_pages")
    var totalPages: Int,
    @SerialName("results")
    var movieData: List<MovieData>
)