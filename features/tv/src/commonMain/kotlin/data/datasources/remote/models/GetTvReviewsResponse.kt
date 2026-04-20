package com.hrudhaykanth116.tv.data.datasources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTvReviewsResponse(
    val id: Int? = null,
    val page: Int? = null,
    @SerialName("results")
    val reviewDetails: List<ReviewDetails> = listOf(),
    val total_pages: Int? = null,
    val total_results: Int? = null
) {
    @Serializable
    data class ReviewDetails(
        val author: String? = null,
        val author_details: AuthorDetails? = null,
        val content: String? = null,
        val created_at: String? = null,
        val id: String? = null,
        val updated_at: String? = null,
        val url: String? = null
    ) {
        @Serializable
        data class AuthorDetails(
            val avatar_path: String? = null,
            val name: String? = null,
            val rating: Double? = null,
            val username: String? = null
        )
    }
}