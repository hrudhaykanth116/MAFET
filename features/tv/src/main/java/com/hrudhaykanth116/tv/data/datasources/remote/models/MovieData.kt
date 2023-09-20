package com.hrudhaykanth116.tv.data.datasources.remote.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
@Entity
data class MovieData(
    @PrimaryKey
    var id: Int?,
    var adult: Boolean?,
    @Json(name = "backdrop_path")
    var backdropPath: String?,
    @Json(name = "genre_ids")
    var genreIds: List<Int>,
    @Json(name = "original_language")
    var originalLanguage: String?,
    @Json(name = "original_title")
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double,
    @Json(name = "poster_path")
    var posterPath: String?,
    @Json(name = "release_date")
    var releaseDate: String?,
    var title: String?,
    var video: Boolean?,
    @Json(name = "vote_average")
    var voteAverage: Double?,
    @Json(name = "vote_count")
    var voteCount: Int?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other == this){
            return true
        }
        return (other as MovieData).id == this.id
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + genreIds.hashCode()
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + (voteAverage?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        return result
    }


    companion object{
        public val diffUtillCallback = object: DiffUtil.ItemCallback<MovieData>(){
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

}