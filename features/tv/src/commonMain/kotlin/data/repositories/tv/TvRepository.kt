package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import com.hrudhaykanth116.tv.data.mappers.toDomain
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult
import com.hrudhaykanth116.tv.domain.repository.ITvRepository
import kotlinx.coroutines.CoroutineDispatcher

class TvRepository(
    private val remoteDataSource: TvRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), ITvRepository {

    override suspend fun getPopularTvShows(pageId: Int): DomainResult<TvShowPagedResult> =
        fetchResult {
            remoteDataSource.getPopularTvShows(pageId)
        }.map { it.toDomain() }

    override suspend fun getTopRatedTvShows(pageId: Int): DomainResult<TvShowPagedResult> =
        fetchResult {
            remoteDataSource.getTopRatedTvShows(pageId)
        }.map { it.toDomain() }

    override suspend fun getAiringTodayShows(pageId: Int): DomainResult<TvShowPagedResult> =
        fetchResult {
            remoteDataSource.getAiringTodayShows(pageId)
        }.map { it.toDomain() }

    override suspend fun getTrendingTv(timeWindow: String, pageId: Int): DomainResult<TvShowPagedResult> =
        fetchResult {
            remoteDataSource.getTrendingTv(timeWindow, pageId)
        }.map { it.toDomain() }
}
