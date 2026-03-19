package com.hrudhaykanth116.tv.data.datasources.remote.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDataPagedResponse(
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val tvShowsList: List<TvShowData> = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
)

