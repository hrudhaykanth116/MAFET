package com.hrudhaykanth116.tv.data.models.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieVideosResponse(
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "results")
    val results: List<MovieVideo?>? = listOf()
){

    @JsonClass(generateAdapter = true)
    data class MovieVideo(
        @Json(name = "id")
        val id: String? = "",
        @Json(name = "iso_3166_1")
        val iso31661: String? = "",
        @Json(name = "iso_639_1")
        val iso6391: String? = "",
        @Json(name = "key")
        val key: String? = "",
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "site")
        val site: String? = "",
        @Json(name = "size")
        val size: Int? = 0,
        @Json(name = "type")
        val type: String? = ""
    )

}

