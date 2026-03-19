package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse

class AiringTodayShowsRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor
) : NetworkDataSource() {

    suspend fun getAiringTodayShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getAiringTodayShows(pageId)
        }

}