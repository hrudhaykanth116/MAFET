package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowSimilarShowsRepository constructor(
    private val tmdbApiService: TmdbApiServiceKtor
) {

    fun getTvShowsPagingData(tvShowId: Int): Flow<List<TvShowData>> = flow {
        val result = tmdbApiService.getTvShowsSimilar(tvShowId = tvShowId, page = 1)
        if (result.isSuccess) {
            emit(result.getOrNull()?.tvShowsList ?: emptyList())
        } else {
            emit(emptyList())
        }
    }
}
