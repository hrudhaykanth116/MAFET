package com.hrudhaykanth116.tv.data.datasources.remote.models
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowDataPagedResponse(
    @Json(name = "page")
    val page: Int? = 0,
    @Json(name = "results")
    val tvShowsList: List<TvShowData> = listOf(),
    @Json(name = "total_pages")
    val totalPages: Int? = 0,
    @Json(name = "total_results")
    val totalResults: Int? = 0
)

