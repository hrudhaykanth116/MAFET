package com.hrudhaykanth116.tv.data.datasources.remote.models.genres
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetTvGenresResponse(
    @SerialName("genres")
    val genres: List<Genre>? = null
)