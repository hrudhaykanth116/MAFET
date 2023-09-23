package com.hrudhaykanth116.tv.data.datasources.remote.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class TvShowData(
    @Json(name = "backdrop_path")
    val backdropPath: String? = "",
    @Json(name = "first_air_date")
    val firstAirDate: String? = "",
    @Json(name = "genre_ids")
    val genreIds: List<Int>? = listOf(),
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "origin_country")
    val originCountry: List<String>? = listOf(),
    @Json(name = "original_language")
    val originalLanguage: String? = "",
    @Json(name = "original_name")
    val originalName: String? = "",
    @Json(name = "overview")
    val overview: String? = "",
    @Json(name = "popularity")
    val popularity: Double? = 0.0,
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "vote_average")
    val voteAverage: Double? = 0.0,
    @Json(name = "vote_count")
    val voteCount: Int? = 0
) : Parcelable {

    // override fun equals(other: Any?): Boolean {
    //     if (this === other) return true
    //     if (javaClass != other?.javaClass) return false
    //
    //     other as TvShowData
    //
    //     if (id != other.id) return false
    //
    //     return true
    // }
    //
    // override fun hashCode(): Int {
    //     return id ?: 0
    // }

    companion object{
        public val diffUtillCallback = object: DiffUtil.ItemCallback<TvShowData>(){
            override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

}