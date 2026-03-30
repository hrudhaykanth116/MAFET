package com.hrudhaykanth116.tv.data.repositories.tv

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
        fetchResult {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }.map { it.toDomain() }

    override suspend fun searchTvShow(query: String): DomainResult<TvShowSearchResult> =
        fetchResult {
            tvShowsRemoteDataSource.searchTvShow(query)
        }.map { it.toDomain() }

    override suspend fun getTvGenres(): DomainResult<GetTvGenresResponse> =
        fetchResult {
            tvShowsRemoteDataSource.getTvGenres()
        }

    override suspend fun getTvImages(tvId: Int): DomainResult<GetTvImagesResponse> =
        fetchResult {
            tvShowsRemoteDataSource.getTvImages(tvId)
        }

    override suspend fun getTvShowVideos(tvId: Int): DomainResult<GetTvVideosResponse> =
        fetchResult {
            tvShowsRemoteDataSource.getTvShowVideos(tvId)
        }

    override suspend fun getTvShowsSimilar(
        tvId: Int,
        pageId: Int,
    ): DomainResult<TvShowPagedResult> =
        fetchResult {
            tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
        }.map { it.toDomain() }

    override suspend fun getTvReviews(
        tvId: Int,
        pageId: Int,
    ): DomainResult<GetTvReviewsResponse> =
        fetchResult {
            tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
        }

    override suspend fun getTvCredits(tvId: Int): DomainResult<GetTvCreditsResponse> =
        fetchResult {
            tvShowsRemoteDataSource.getTvCredits(tvId)
        }

}