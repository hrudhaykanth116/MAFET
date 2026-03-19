package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults

class TvShowsRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor
) : NetworkDataSource() {

    suspend fun fetchTvShowDetails(tvShowId: Int): ApiResultWrapper<TvShowDetails> =
        getResult {
            tmdbApiService.getTvShowDetails(tvShowId)
        }

    suspend fun searchTvShow(query: String): ApiResultWrapper<TvShowSearchResults> =
        getResult {
            tmdbApiService.searchTv(query)
        }

    suspend fun getTvGenres(): ApiResultWrapper<GetTvGenresResponse> =
        getResult {
            tmdbApiService.getTvGenres()
        }

    suspend fun getTvImages(tvId: Int): ApiResultWrapper<GetTvImagesResponse> =
        getResult {
            tmdbApiService.getTvShowImages(tvId)
        }

    suspend fun getTvShowVideos(tvId: Int): ApiResultWrapper<GetTvVideosResponse> =
        getResult {
            tmdbApiService.getTvShowVideos(tvId)
        }

    suspend fun getTvShowsSimilar(
        tvId: Int,
        pageId: Int,
    ): ApiResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tmdbApiService.getTvShowsSimilar(tvId, pageId)
        }

    suspend fun getTvReviews(tvId: Int, pageId: Int): ApiResultWrapper<GetTvReviewsResponse> =
        getResult {
            tmdbApiService.getTvReviews(tvId, pageId)
        }

    suspend fun getTvCredits(tvId: Int): ApiResultWrapper<GetTvCreditsResponse> =
        getResult {
            tmdbApiService.getTvCredits(tvId)
        }

}