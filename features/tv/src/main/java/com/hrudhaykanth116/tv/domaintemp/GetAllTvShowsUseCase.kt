package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.tv.CategorisedTvShows
import com.hrudhaykanth116.tv.data.repositories.tv.TvRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllTvShowsUseCase @Inject constructor(private val repository: TvRepository) {

    suspend operator fun invoke(): DataResult<CategorisedTvShows> = coroutineScope {
        val popularDeferred = async { repository.getPopularTvShows(1) }
        val topRatedDeferred = async { repository.getTopRatedTvShows(1) }
        val airingTodayDeferred = async { repository.getAiringTodayShows(1) }
        val trendingDeferred = async { repository.getTrendingTv("day") }

        val popularResult: DataResult<TvShowDataPagedResponse> = popularDeferred.await()
        val topRatedResult: DataResult<TvShowDataPagedResponse> = topRatedDeferred.await()
        val airingTodayResult: DataResult<TvShowDataPagedResponse> = airingTodayDeferred.await()
        val trendingResult: DataResult<TvShowDataPagedResponse> = trendingDeferred.await()

        val popular = extractList(popularResult)
        val topRated = extractList(topRatedResult)
        val airingToday = extractList(airingTodayResult)
        val trending = extractList(trendingResult)

        val allEmpty = popular.isEmpty() && topRated.isEmpty() &&
                airingToday.isEmpty() && trending.isEmpty()

        return@coroutineScope if (allEmpty) {
            val firstError =
                listOf(popularResult, topRatedResult, airingTodayResult, trendingResult)
                    .firstOrNull { it is DataResult.Error } as? DataResult.Error
            firstError ?: DataResult.Error(UIText.Text("Unknown error"))
        } else {
            DataResult.Success(
                CategorisedTvShows(
                    popular = popular,
                    topRated = topRated,
                    airingToday = airingToday,
                    trending = trending
                )
            )
        }
    }

    private fun extractList(result: DataResult<*>): List<TvShowData> {
        return when (result) {
            is DataResult.Success -> when (val data = result.data) {
                is TvShowDataPagedResponse -> data.tvShowsList
                else -> emptyList()
            }

            else -> emptyList()
        }
    }
}
