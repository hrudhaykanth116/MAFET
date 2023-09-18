package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.models.models.TvShowDataPagedResponse
import javax.inject.Inject

class AiringTodayShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis,
) : NetworkDataSource() {

    suspend fun getAiringTodayShows(pageId: Int): DataResult<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getAiringTodayShows(pageId)
        }
    }

}