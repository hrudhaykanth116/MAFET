package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.mappers.toDomainResult
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher

class TvShowsRepository(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), ITvShowsRepository {

    override suspend fun getTvShowDetails(tvShowId: Int): DomainResult<TvShowDetails> =
        getResult {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }.toDomainResult()

    override suspend fun searchTvShow(query: String): DomainResult<TvShowSearchResults> =
        getResult {
            tvShowsRemoteDataSource.searchTvShow(query)
        }.toDomainResult()

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
    ): DomainResult<TvShowDataPagedResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
        }.toDomainResult()

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