package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AiringTodayShowsRepository(
    private val tmdbApiService: TmdbApiServiceKtor,
) {

    fun getTvShowsPagingData(): Flow<List<TvShowData>> = flow {
        val result = tmdbApiService.getAiringTodayShows(pageId = 1)
        if (result.isSuccess) {
            emit(result.getOrNull()?.tvShowsList ?: emptyList())
        } else {
            emit(emptyList())
        }
    }
}
