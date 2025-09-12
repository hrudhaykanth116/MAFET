package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import javax.inject.Inject

class AiringTodayShowsRemoteDataSource @Inject constructor(
    private val retroApis: RetroApis,
) : NetworkDataSource() {

    suspend fun getAiringTodayShows(pageId: Int): ApiResultWrapper<TvShowDataPagedResponse> {
        return getResult {
            retroApis.getAiringTodayShows(pageId)
        }
    }

}