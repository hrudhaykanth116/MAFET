package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.mappers.toDomainResult
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
        getResult {
            remoteDataSource.getPopularTvShows(pageId)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun getTopRatedTvShows(pageId: Int): DomainResult<TvShowPagedResult> =
        getResult {
            remoteDataSource.getTopRatedTvShows(pageId)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun getAiringTodayShows(pageId: Int): DomainResult<TvShowPagedResult> =
        getResult {
            remoteDataSource.getAiringTodayShows(pageId)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun getTrendingTv(timeWindow: String, pageId: Int): DomainResult<TvShowPagedResult> =
        getResult {
            remoteDataSource.getTrendingTv(timeWindow, pageId)
        }.toDomainResult().map { it.toDomain() }
}
