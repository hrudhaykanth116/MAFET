package com.hrudhaykanth116.tv.data.datasources.remote.models.search
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowSearchResults(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val tvShowDataList: List<TvShowData?>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)