package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.mappers.toDomainResult
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.mappers.toDomain
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult
import com.hrudhaykanth116.tv.domain.models.TvShowSearchResult
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import kotlinx.coroutines.CoroutineDispatcher

class TvShowsRepository(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), ITvShowsRepository {

    override suspend fun getTvShowDetails(tvShowId: Int): DomainResult<TvShowDetail> =
        getResult {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun searchTvShow(query: String): DomainResult<TvShowSearchResult> =
        getResult {
            tvShowsRemoteDataSource.searchTvShow(query)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun getTvGenres(): DomainResult<GetTvGenresResponse> = getResult {
        tvShowsRemoteDataSource.getTvGenres()
    }.toDomainResult()

    override suspend fun getTvImages(tvId: Int): DomainResult<GetTvImagesResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvImages(tvId)
        }.toDomainResult()

    override suspend fun getTvShowVideos(tvId: Int): DomainResult<GetTvVideosResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvShowVideos(tvId)
        }.toDomainResult()

    override suspend fun getTvShowsSimilar(
        tvId: Int,
        pageId: Int,
    ): DomainResult<TvShowPagedResult> =
        getResult {
            tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
        }.toDomainResult().map { it.toDomain() }

    override suspend fun getTvReviews(
        tvId: Int,
        pageId: Int,
    ): DomainResult<GetTvReviewsResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
        }.toDomainResult()

    override suspend fun getTvCredits(tvId: Int): DomainResult<GetTvCreditsResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvCredits(tvId)
        }.toDomainResult()

}