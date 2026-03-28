package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DiscoverTvShowsRepository(
    private val tmdbApiService: TmdbApiServiceKtor,
) {

    fun getTvShowsPagingData(genres: List<Genre>?): Flow<List<TvShowData>> = flow {
        val genreIds = if (genres.isNullOrEmpty()) {
            "10759|99|16|10762|10765"
        } else {
            genres.joinToString { "${it.id}" }
        }
        val result = tmdbApiService.discoverTv(page = 1, genres = genreIds)
        if (result.isSuccess) {
            emit(result.getOrNull()?.tvShowsList ?: emptyList())
        } else {
            emit(emptyList())
        }
    }
}
