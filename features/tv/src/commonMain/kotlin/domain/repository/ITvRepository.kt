package com.hrudhaykanth116.tv.domain.repository

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult

interface ITvRepository {
    suspend fun getPopularTvShows(pageId: Int): DomainResult<TvShowPagedResult>
    suspend fun getTopRatedTvShows(pageId: Int): DomainResult<TvShowPagedResult>
    suspend fun getAiringTodayShows(pageId: Int): DomainResult<TvShowPagedResult>
    suspend fun getTrendingTv(timeWindow: String, pageId: Int = 1): DomainResult<TvShowPagedResult>
}
