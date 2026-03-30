package com.hrudhaykanth116.media.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CuratedPhotosResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("photos")
    val photos: List<PhotoResponse>,
    @SerialName("next_page")
    val nextPage: String? = null
)
