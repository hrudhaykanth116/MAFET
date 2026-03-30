package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.tv.CategorisedTvShows
import com.hrudhaykanth116.tv.data.repositories.tv.TvRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetAllTvShowsUseCase(private val repository: TvRepository) {

    suspend operator fun invoke(): DomainResult<CategorisedTvShows> = coroutineScope {
        val popularDeferred = async { repository.getPopularTvShows(1) }
        val topRatedDeferred = async { repository.getTopRatedTvShows(1) }
        val airingTodayDeferred = async { repository.getAiringTodayShows(1) }
        val trendingDeferred = async { repository.getTrendingTv("day") }

        val popularResult: DomainResult<TvShowDataPagedResponse> = popularDeferred.await()
        val topRatedResult: DomainResult<TvShowDataPagedResponse> = topRatedDeferred.await()
        val airingTodayResult: DomainResult<TvShowDataPagedResponse> = airingTodayDeferred.await()
        val trendingResult: DomainResult<TvShowDataPagedResponse> = trendingDeferred.await()

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

    private fun extractList(result: DomainResult<*>): List<TvShowData> {
        return when (result) {
            is DomainResult.Success -> when (val data = result.data) {
                is TvShowDataPagedResponse -> data.tvShowsList
                else -> emptyList()
            }

            else -> emptyList()
        }
    }
}
