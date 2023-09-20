package com.hrudhaykanth116.tv.data.datasources.remote.models.genres
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GetTvGenresResponse(
    @Json(name = "genres")
    val genres: List<Genre>? = null
)