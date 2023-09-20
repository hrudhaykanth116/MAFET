package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.TvApisService
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import javax.inject.Inject

class TvShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis,
    private val tvApisService: TvApisService,
) : NetworkDataSource() {

    suspend fun fetchTvShowDetails(tvShowId: Int): DataResult<TvShowDetails> {
        return getResult {
            retroApis.getTvShowDetails(tvShowId)
        }
    }

    suspend fun searchTvShow(query: String): DataResult<TvShowSearchResults> {
        return getResult {
            retroApis.searchTv(query)
        }
    }

    suspend fun getTvGenres(): DataResult<GetTvGenresResponse> {
        return getResult {
            retroApis.getTvGenres()
        }
    }

    suspend fun getTvImages(tvId: Int): DataResult<GetTvImagesResponse> {
        return getResult {
            tvApisService.getTvShowImages(tvId)
        }
    }

    suspend fun getTvShowVideos(tvId: Int): DataResult<GetTvVideosResponse> {
        return getResult {
            tvApisService.getTvShowVideos(tvId)
        }
    }

    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): DataResult<TvShowDataPagedResponse> {
        return getResult {
            tvApisService.getTvShowsSimilar(tvId, pageId)
        }
    }

    suspend fun getTvReviews(tvId: Int, pageId: Int): DataResult<GetTvReviewsResponse> {
        return getResult {
            tvApisService.getTvReviews(tvId, pageId)
        }
    }

    suspend fun getTvCredits(tvId: Int): DataResult<GetTvCreditsResponse> {
        return getResult {
            tvApisService.getTvCredits(tvId)
        }
    }

}