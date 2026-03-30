package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.mappers.toDomainResult
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher

class TvRepository(
    private val remoteDataSource: TvRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher) {

    suspend fun getPopularTvShows(pageId: Int): DomainResult<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getPopularTvShows(pageId)
        }.toDomainResult()

    suspend fun getTopRatedTvShows(pageId: Int): DomainResult<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getTopRatedTvShows(pageId)
        }.toDomainResult()

    suspend fun getAiringTodayShows(pageId: Int): DomainResult<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getAiringTodayShows(pageId)
        }.toDomainResult()

    suspend fun getTrendingTv(timeWindow: String, pageId: Int = 1): DomainResult<TvShowDataPagedResponse> =
        getResult {
            remoteDataSource.getTrendingTv(timeWindow, pageId)
        }.toDomainResult()
}
