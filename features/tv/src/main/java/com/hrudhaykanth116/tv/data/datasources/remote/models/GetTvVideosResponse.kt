package com.hrudhaykanth116.tv.data.datasources.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class GetTvVideosResponse(
    val id: Int? = null,
    val results: List<Result?>? = null
) {
    @Serializable
    data class Result(
        val id: String? = null,
        val iso_3166_1: String? = null,
        val iso_639_1: String? = null,
        val key: String? = null,
        val name: String? = null,
        val official: Boolean? = null,
        val published_at: String? = null,
        val site: String? = null,
        val size: Int? = null,
        val type: String? = null
    )
}