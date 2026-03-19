package com.hrudhaykanth116.tv.data.datasources.remote.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class TvShowData(
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("first_air_date")
    val firstAirDate: String? = "",
    @SerialName("genre_ids")
    val genreIds: List<Int>? = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String? = "",
    @SerialName("origin_country")
    val originCountry: List<String>? = listOf(),
    @SerialName("original_language")
    val originalLanguage: String? = "",
    @SerialName("original_name")
    val originalName: String? = "",
    @SerialName("overview")
    val overview: String? = "",
    @SerialName("popularity")
    val popularity: Double? = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
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