package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.AiringTodayShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.models.models.TvShowDataPagedResponse
import javax.inject.Inject

class AiringTodayShowsRepository @Inject constructor(
    private val airingTodayShowsRemoteDataSource: AiringTodayShowsRemoteDataSource,
) {

    suspend fun getAiringTodayShows(pageId: Int): DataResult<TvShowDataPagedResponse> {
        return airingTodayShowsRemoteDataSource.getAiringTodayShows(pageId)
    }

}