package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRemoteDataSource @Inject constructor(private val retroApis: RetroApis): NetworkDataSource() {

    suspend fun getPopularTvShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getPopularTvShows(pageId)
        }
    }

    suspend fun getTopRatedTvShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getTopRatedTvShows(pageId)
        }
    }

    suspend fun getAiringTodayShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getAiringTodayShows(pageId)
        }
    }

    suspend fun getTrendingTv(timeWindow: String): ApiResultWrapper<TvShowDataPagedResponse> {
        return getResult {
            retroApis.trendingTv(timeWindow)
        }
    }
}
