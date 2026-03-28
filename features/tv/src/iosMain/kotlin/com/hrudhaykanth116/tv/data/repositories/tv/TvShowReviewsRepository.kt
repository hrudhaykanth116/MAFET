package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowReviewsRepository constructor(
    private val tmdbApiService: TmdbApiServiceKtor
) {

    fun getTvShowsPagingData(tvShowId: Int): Flow<List<GetTvReviewsResponse.ReviewDetails>> = flow {
        val result = tmdbApiService.getTvReviews(tvShowId = tvShowId, page = 1)
        if (result.isSuccess) {
            emit(result.getOrNull()?.reviewDetails ?: emptyList())
        } else {
            emit(emptyList())
        }
    }
}
