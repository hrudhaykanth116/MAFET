package com.hrudhaykanth116.tv.data.datasources.remote.models.discover

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscoverResult(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String?>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
){

    companion object{
        // hrudhay_check_list: 12/07/21 Ignoring the diff as list stays in the same position.
        public val diffUtilCallback = object: DiffUtil.ItemCallback<DiscoverResult>(){
            override fun areItemsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
                // return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
                // return oldItem == newItem
                return false
            }

        }
    }

}