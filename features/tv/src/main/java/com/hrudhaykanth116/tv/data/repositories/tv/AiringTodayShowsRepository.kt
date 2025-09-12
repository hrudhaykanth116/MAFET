package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.AiringTodayShowsRemoteDataSource
import javax.inject.Inject

class AiringTodayShowsRepository @Inject constructor(
    private val airingTodayShowsRemoteDataSource: AiringTodayShowsRemoteDataSource,
): BaseRepository(

) {

    suspend fun getAiringTodayShows(pageId: Int): RepoResultWrapper<TvShowDataPagedResponse> = getResult{
        airingTodayShowsRemoteDataSource.getAiringTodayShows(pageId)
    }

}