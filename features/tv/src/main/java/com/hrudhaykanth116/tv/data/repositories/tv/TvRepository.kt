package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.data.repository.toRepoResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher

class TvRepository(
    private val remoteDataSource: TvRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher) {

    suspend fun getPopularTvShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getPopularTvShows(pageId)
        }

    suspend fun getTopRatedTvShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getTopRatedTvShows(pageId)
        }

    suspend fun getAiringTodayShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getAiringTodayShows(pageId)
        }

    suspend fun getTrendingTv(timeWindow: String): RepoResultWrapper<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getTrendingTv(timeWindow)
        }
}
