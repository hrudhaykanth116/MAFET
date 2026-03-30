package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.repositories.tv.TvRepository
import com.hrudhaykanth116.tv.domain.models.CategorisedTvShows
import com.hrudhaykanth116.tv.domain.models.TvShow
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetAllTvShowsUseCase(private val repository: TvRepository) {

    suspend operator fun invoke(): DomainResult<CategorisedTvShows> = coroutineScope {
        val popularDeferred = async { repository.getPopularTvShows(1) }
        val topRatedDeferred = async { repository.getTopRatedTvShows(1) }
        val airingTodayDeferred = async { repository.getAiringTodayShows(1) }
        val trendingDeferred = async { repository.getTrendingTv("day") }

        val popularResult: DomainResult<TvShowPagedResult> = popularDeferred.await()
        val topRatedResult: DomainResult<TvShowPagedResult> = topRatedDeferred.await()
        val airingTodayResult: DomainResult<TvShowPagedResult> = airingTodayDeferred.await()
        val trendingResult: DomainResult<TvShowPagedResult> = trendingDeferred.await()

        val popular = extractList(popularResult)
        val topRated = extractList(topRatedResult)
        val airingToday = extractList(airingTodayResult)
        val trending = extractList(trendingResult)

        val allEmpty = popular.isEmpty() && topRated.isEmpty() &&
                airingToday.isEmpty() && trending.isEmpty()

        return@coroutineScope if (allEmpty) {
            val firstError =
                listOf(popularResult, topRatedResult, airingTodayResult, trendingResult)
                    .firstOrNull { it is DomainResult.Error } as? DomainResult.Error
            firstError ?: DomainResult.Error(DomainError.Unknown(message = "Failed to fetch TV shows"))
        } else {
            DomainResult.Success(
                CategorisedTvShows(
                    popular = popular,
                    topRated = topRated,
                    airingToday = airingToday,
                    trending = trending
                )
            )
        }
    }

    private fun extractList(result: DomainResult<*>): List<TvShow> {
        return when (result) {
            is DomainResult.Success -> when (val data = result.data) {
                is TvShowPagedResult -> data.tvShows
                else -> emptyList()
            }

            else -> emptyList()
        }
    }
}
