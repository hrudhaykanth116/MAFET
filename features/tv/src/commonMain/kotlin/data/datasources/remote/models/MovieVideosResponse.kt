package com.hrudhaykanth116.tv.data.datasources.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideosResponse(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("results")
    val results: List<MovieVideo?>? = listOf()
){

    @Serializable
    data class MovieVideo(
        @SerialName("id")
        val id: String? = "",
        @SerialName("iso_3166_1")
        val iso31661: String? = "",
        @SerialName("iso_639_1")
        val iso6391: String? = "",
        @SerialName("key")
        val key: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("site")
        val site: String? = "",
        @SerialName("size")
        val size: Int? = 0,
        @SerialName("type")
        val type: String? = ""
    )

}

