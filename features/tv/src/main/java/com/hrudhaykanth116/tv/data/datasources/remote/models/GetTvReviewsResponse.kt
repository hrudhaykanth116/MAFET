package com.hrudhaykanth116.tv.data.datasources.remote.models

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvReviewsResponse(
    val id: Int? = null,
    val page: Int? = null,
    @Json(name = "results")
    val reviewDetails: List<ReviewDetails> = listOf(),
    val total_pages: Int? = null,
    val total_results: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class ReviewDetails(
        val author: String? = null,
        val author_details: AuthorDetails? = null,
        val content: String? = null,
        val created_at: String? = null,
        val id: String? = null,
        val updated_at: String? = null,
        val url: String? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class AuthorDetails(
            val avatar_path: String? = null,
            val name: String? = null,
            val rating: Int? = null,
            val username: String? = null
        )

        companion object{
            public val diffUtillCallback = object: DiffUtil.ItemCallback<ReviewDetails>(){
                override fun areItemsTheSame(oldItem: ReviewDetails, newItem: ReviewDetails): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ReviewDetails, newItem: ReviewDetails): Boolean {
                    return oldItem.equals(newItem)
                }

            }
        }

    }
}