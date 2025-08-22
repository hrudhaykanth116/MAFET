package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor(private val remoteDataSource: TvRemoteDataSource) {

    suspend fun getPopularTvShows(pageId: Int): DataResult<TvShowDataPagedResponse> {
        return remoteDataSource.getPopularTvShows(pageId)
    }

    suspend fun getTopRatedTvShows(pageId: Int): DataResult<TvShowDataPagedResponse> {
        return remoteDataSource.getTopRatedTvShows(pageId)
    }

    suspend fun getAiringTodayShows(pageId: Int): DataResult<TvShowDataPagedResponse> {
        return remoteDataSource.getAiringTodayShows(pageId)
    }

    suspend fun getTrendingTv(timeWindow: String): DataResult<TvShowDataPagedResponse> {
        return remoteDataSource.getTrendingTv(timeWindow)
    }
}
