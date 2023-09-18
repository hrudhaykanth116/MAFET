package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.models.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.models.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.models.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.models.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.models.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.models.models.TvShowDetails
import com.hrudhaykanth116.tv.data.models.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.models.models.search.TvShowSearchResults
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
) {

    suspend fun getTvShowDetails(tvShowId: Int): DataResult<TvShowDetails> {
        return tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
    }

    suspend fun searchTvShow(query: String): DataResult<TvShowSearchResults> {
        return tvShowsRemoteDataSource.searchTvShow(query)
    }

    suspend fun getTvGenres(): DataResult<GetTvGenresResponse> {
        return tvShowsRemoteDataSource.getTvGenres()
    }

    suspend fun getTvImages(tvId: Int): DataResult<GetTvImagesResponse> {
        return tvShowsRemoteDataSource.getTvImages(tvId)
    }

    suspend fun getTvShowVideos(tvId: Int): DataResult<GetTvVideosResponse> {
        return tvShowsRemoteDataSource.getTvShowVideos(tvId)
    }

    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): DataResult<TvShowDataPagedResponse> {
        return tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
    }

    suspend fun getTvReviews(tvId: Int, pageId: Int): DataResult<GetTvReviewsResponse> {
        return tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
    }

    suspend fun getTvCredits(tvId: Int): DataResult<GetTvCreditsResponse> {
        return tvShowsRemoteDataSource.getTvCredits(tvId)
    }

}