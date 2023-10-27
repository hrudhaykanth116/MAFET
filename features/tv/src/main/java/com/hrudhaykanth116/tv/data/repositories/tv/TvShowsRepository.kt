package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
) {

    // TODO: Make it injectable
    private val dispatcher = Dispatchers.IO

    suspend fun getTvShowDetails(tvShowId: Int): DataResult<TvShowDetails> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }

    suspend fun searchTvShow(query: String): DataResult<TvShowSearchResults> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.searchTvShow(query)
        }

    suspend fun getTvGenres(): DataResult<GetTvGenresResponse> = withContext(dispatcher) {
        tvShowsRemoteDataSource.getTvGenres()
    }

    suspend fun getTvImages(tvId: Int): DataResult<GetTvImagesResponse> = withContext(dispatcher) {
        tvShowsRemoteDataSource.getTvImages(tvId)
    }

    suspend fun getTvShowVideos(tvId: Int): DataResult<GetTvVideosResponse> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.getTvShowVideos(tvId)
        }

    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): DataResult<TvShowDataPagedResponse> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
        }

    suspend fun getTvReviews(tvId: Int, pageId: Int): DataResult<GetTvReviewsResponse> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
        }

    suspend fun getTvCredits(tvId: Int): DataResult<GetTvCreditsResponse> =
        withContext(dispatcher) {
            tvShowsRemoteDataSource.getTvCredits(tvId)
        }

}