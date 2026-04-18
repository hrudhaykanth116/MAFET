package com.hrudhaykanth116.tv.data.repositories.tv

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsPagingSource
import com.hrudhaykanth116.tv.domain.models.TvCategory
import kotlinx.coroutines.flow.Flow

class TvPagingRepository(
    private val apiService: TmdbApiServiceKtor,
) {

    fun getTvShowsPagingData(category: TvCategory): Flow<PagingData<TvShowData>> = pagedFlow { page ->
        val response = when (category) {
            TvCategory.POPULAR -> apiService.getPopularTvShows(page)
            TvCategory.TOP_RATED -> apiService.getTopRatedTvShows(page)
            TvCategory.TRENDING -> apiService.getTrendingTv(timeWindow = "day", pageId = page)
            TvCategory.AIRING_TODAY -> apiService.getAiringTodayShows(page)
        }
        response.map { it.tvShowsList }
    }

    fun getDiscoverPagingData(genres: List<Genre>?): Flow<PagingData<TvShowData>> {
        val genreIds = if (genres.isNullOrEmpty()) DEFAULT_DISCOVER_GENRE_IDS
        else genres.joinToString(separator = ",") { "${it.id}" }

        return pagedFlow(initialPageRange = SMALL_INITIAL_PAGE_RANGE) { page ->
            apiService.discoverTv(page = page, genres = genreIds).map { it.tvShowsList }
        }
    }

    fun getSimilarShowsPagingData(tvShowId: Int): Flow<PagingData<TvShowData>> =
        pagedFlow(initialPageRange = SMALL_INITIAL_PAGE_RANGE) { page ->
            apiService.getTvShowsSimilar(tvShowId = tvShowId, page = page).map { it.tvShowsList }
        }

    fun getReviewsPagingData(tvShowId: Int): Flow<PagingData<GetTvReviewsResponse.ReviewDetails>> =
        pagedFlow(initialPageRange = SMALL_INITIAL_PAGE_RANGE) { page ->
            apiService.getTvReviews(tvShowId = tvShowId, page = page).map { it.reviewDetails }
        }

    private fun <T : Any> pagedFlow(
        initialPageRange: IntRange = 1..20,
        fetch: suspend (Int) -> Result<List<T>>,
    ): Flow<PagingData<T>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE,
        ),
        pagingSourceFactory = {
            TvShowsPagingSource(initialPageRange = initialPageRange, fetchPage = fetch)
        },
    ).flow

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_LOAD_SIZE = 2 * PAGE_SIZE
        private const val DEFAULT_DISCOVER_GENRE_IDS = "10759,99,16,10762,10765"
        private val SMALL_INITIAL_PAGE_RANGE = 1..10
    }
}
