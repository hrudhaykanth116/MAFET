package com.hrudhaykanth116.tv.data.models.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvImagesResponse(
    val backdrops: List<ImageObj?> = listOf(),
    val id: Int? = null,
    val posters: List<ImageObj?> = listOf()
) {

    @JsonClass(generateAdapter = true)
    data class ImageObj(
        val aspect_ratio: Double? = null,
        val file_path: String? = null,
        val height: Int? = null,
        val iso_639_1: String? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null,
        val width: Int? = null
    )

}