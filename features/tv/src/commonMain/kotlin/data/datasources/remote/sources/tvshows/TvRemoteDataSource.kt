package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse

class TvRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor
) : NetworkDataSource() {

    suspend fun getPopularTvShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getPopularTvShows(pageId)
        }

    suspend fun getTopRatedTvShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getTopRatedTvShows(pageId)
        }

    suspend fun getAiringTodayShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getAiringTodayShows(pageId)
        }

    suspend fun getTrendingTv(timeWindow: String, pageId: Int = 1): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getTrendingTv(timeWindow, pageId)
        }
}
