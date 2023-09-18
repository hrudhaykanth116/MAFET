package com.hrudhaykanth116.tv.data.models.models.search
import com.hrudhaykanth116.tv.data.models.models.TvShowData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowSearchResults(
    @Json(name = "page")
    val page: Int? = null,
    @Json(name = "results")
    val tvShowDataList: List<TvShowData?>? = null,
    @Json(name = "total_pages")
    val totalPages: Int? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null
)