package com.hrudhaykanth116.media.data.models

import com.squareup.moshi.Json

data class CuratedPhotosResponse(
    val page: Int,
    @param:Json(name = "per_page") val perPage: Int,
    @param:Json(name = "total_results") val totalResults: Int,
    val photos: List<PhotoResponse>,
    @param:Json(name = "next_page") val nextPage: String?
)
