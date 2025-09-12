package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.common.mappers.toRepoResult
import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor(
    private val remoteDataSource: TvRemoteDataSource,
) : BaseRepository() {

    suspend fun getPopularTvShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> {
        return remoteDataSource.getPopularTvShows(pageId).toRepoResult()
    }

    suspend fun getTopRatedTvShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> {
        return remoteDataSource.getTopRatedTvShows(pageId).toRepoResult()
    }

    suspend fun getAiringTodayShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> {
        return remoteDataSource.getAiringTodayShows(pageId).toRepoResult()
    }

    suspend fun getTrendingTv(timeWindow: String): RepoResultWrapper<TvShowDataPagedResponse> {
        return remoteDataSource.getTrendingTv(timeWindow).toRepoResult()
    }
}
