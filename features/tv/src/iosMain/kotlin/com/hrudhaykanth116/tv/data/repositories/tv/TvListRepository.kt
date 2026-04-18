package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import com.hrudhaykanth116.tv.domain.models.TvCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvListRepository(
    private val apiService: TmdbApiServiceKtor,
) {

    fun getTvShows(category: TvCategory): Flow<List<TvShowData>> = flow {
        val result = when (category) {
            TvCategory.POPULAR -> apiService.getPopularTvShows(pageId = 1)
            TvCategory.TOP_RATED -> apiService.getTopRatedTvShows(pageId = 1)
            TvCategory.TRENDING -> apiService.getTrendingTv(timeWindow = "day", pageId = 1)
            TvCategory.AIRING_TODAY -> apiService.getAiringTodayShows(pageId = 1)
        }
        emit(result.getOrNull()?.tvShowsList.orEmpty())
    }

    fun getDiscoverShows(genres: List<Genre>?): Flow<List<TvShowData>> = flow {
        val genreIds = if (genres.isNullOrEmpty()) DEFAULT_DISCOVER_GENRE_IDS
        else genres.joinToString(separator = ",") { "${it.id}" }
        val result = apiService.discoverTv(page = 1, genres = genreIds)
        emit(result.getOrNull()?.tvShowsList.orEmpty())
    }

    fun getSimilarShows(tvShowId: Int): Flow<List<TvShowData>> = flow {
        val result = apiService.getTvShowsSimilar(tvShowId = tvShowId, page = 1)
        emit(result.getOrNull()?.tvShowsList.orEmpty())
    }

    fun getReviews(tvShowId: Int): Flow<List<GetTvReviewsResponse.ReviewDetails>> = flow {
        val result = apiService.getTvReviews(tvShowId = tvShowId, page = 1)
        emit(result.getOrNull()?.reviewDetails.orEmpty())
    }

    companion object {
        private const val DEFAULT_DISCOVER_GENRE_IDS = "10759,99,16,10762,10765"
    }
}
