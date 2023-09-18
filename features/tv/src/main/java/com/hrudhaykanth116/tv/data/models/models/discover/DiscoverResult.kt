package com.hrudhaykanth116.tv.data.models.models.discover

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverResult(
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @Json(name = "first_air_date")
    val firstAirDate: String? = null,
    @Json(name = "genre_ids")
    val genreIds: List<Int?>? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "origin_country")
    val originCountry: List<String?>? = null,
    @Json(name = "original_language")
    val originalLanguage: String? = null,
    @Json(name = "original_name")
    val originalName: String? = null,
    @Json(name = "overview")
    val overview: String? = null,
    @Json(name = "popularity")
    val popularity: Double? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "vote_average")
    val voteAverage: Double? = null,
    @Json(name = "vote_count")
    val voteCount: Int? = null
){

    companion object{
        // TODO: 12/07/21 Ignoring the diff as list stays in the same position.
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