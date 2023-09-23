package com.hrudhaykanth116.tv.data.datasources.remote.models


import android.os.Parcelable
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class TvShowDetails(
    @Json(name = "backdrop_path")
    val backdropPath: String? = "",
    @Json(name = "created_by")
    val createdBy: List<CreatedBy?>? = listOf(),
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int?>? = listOf(),
    @Json(name = "first_air_date")
    val firstAirDate: String? = "",
    @Json(name = "genres")
    val genres: List<Genre?>? = listOf(),
    @Json(name = "homepage")
    val homepage: String? = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "in_production")
    val inProduction: Boolean? = false,
    @Json(name = "languages")
    val languages: List<String?>? = listOf(),
    @Json(name = "last_air_date")
    val lastAirDate: String? = "",
    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir? = LastEpisodeToAir(),
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "networks")
    val networks: List<Network?>? = listOf(),
    // this json object yet to be analysed.
    // @Json(name = "next_episode_to_air")
    // val nextEpisodeToAir: Any? = Any(),
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int? = 0,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int? = 0,
    @Json(name = "origin_country")
    val originCountry: List<String?>? = listOf(),
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
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany?>? = listOf(),
    @Json(name = "seasons")
    val seasons: List<Season?>? = listOf(),
    @Json(name = "status")
    val status: String? = "",
    @Json(name = "type")
    val type: String? = "",
    @Json(name = "vote_average")
    val voteAverage: Double? = 0.0,
    @Json(name = "vote_count")
    val voteCount: Int? = 0
): Parcelable {

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class CreatedBy(
        @Json(name = "credit_id")
        val creditId: String? = "",
        @Json(name = "gender")
        val gender: Int? = 0,
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "profile_path")
        val profilePath: String? = ""
    ): Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class LastEpisodeToAir(
        @Json(name = "air_date")
        val airDate: String? = "",
        @Json(name = "episode_number")
        val episodeNumber: Int? = 0,
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "overview")
        val overview: String? = "",
        @Json(name = "production_code")
        val productionCode: String? = "",
        @Json(name = "season_number")
        val seasonNumber: Int? = 0,
        @Json(name = "show_id")
        val showId: Int? = 0,
        @Json(name = "still_path")
        val stillPath: String? = "",
        @Json(name = "vote_average")
        val voteAverage: Double? = 0.0,
        @Json(name = "vote_count")
        val voteCount: Int? = 0
    ): Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Network(
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "logo_path")
        val logoPath: String? = "",
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "origin_country")
        val originCountry: String? = ""
    ): Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class ProductionCompany(
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "logo_path")
        val logoPath: String? = "",
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "origin_country")
        val originCountry: String? = ""
    ): Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Season(
        @Json(name = "air_date")
        val airDate: String? = "",
        @Json(name = "episode_count")
        val episodeCount: Int? = 0,
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "overview")
        val overview: String? = "",
        @Json(name = "poster_path")
        val posterPath: String? = "",
        @Json(name = "season_number")
        val seasonNumber: Int? = 0
    ): Parcelable
}