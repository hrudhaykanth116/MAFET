package com.hrudhaykanth116.composeapp.home.dashboard.domain

import com.hrudhaykanth116.composeapp.home.dashboard.models.TvSummary
import com.hrudhaykanth116.tv.data.datasources.local.models.WatchStatus
import com.hrudhaykanth116.tv.domain.usecases.GetMyTvListUseCase
import kotlinx.coroutines.flow.first

class GetDashboardTvUseCase(
    private val getMyTvListUseCase: GetMyTvListUseCase
) {

    suspend operator fun invoke(): TvSummary? {
        val allTvShows = getMyTvListUseCase().first()

        val topRatedWatching = allTvShows
            .filter { it.status == WatchStatus.WATCHING && it.rating != null }
            .maxByOrNull { it.rating ?: 0 }
            ?: return null

        return TvSummary(
            id = topRatedWatching.id,
            name = topRatedWatching.name,
            rating = topRatedWatching.rating ?: 0,
            lastEpisode = formatLastEpisode(
                topRatedWatching.lastWatchedSeason,
                topRatedWatching.lastWatchedEpisode
            )
        )
    }

    private fun formatLastEpisode(season: Int?, episode: Int?): String? {
        if (season == null && episode == null) return null
        return buildString {
            season?.let { append("S$it") }
            episode?.let { append("E$it") }
        }.takeIf { it.isNotEmpty() }
    }
}
