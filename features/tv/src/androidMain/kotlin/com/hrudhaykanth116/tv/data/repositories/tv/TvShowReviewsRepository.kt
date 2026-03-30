package com.hrudhaykanth116.tv.data.repositories.tv

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowReviewsRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import kotlinx.coroutines.flow.Flow

class TvShowReviewsRepository constructor(
    private val tmdbApiService: TmdbApiServiceKtor
) {

    fun getTvShowsPagingData(tvShowId: Int): Flow<PagingData<GetTvReviewsResponse.ReviewDetails>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowReviewsRemoteDataSource(tvShowId, tmdbApiService)
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 12
    }

}