package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult

fun TvShowDataPagedResponse.toDomain(): TvShowPagedResult {
    return TvShowPagedResult(
        page = page ?: 1,
        tvShows = tvShowsList.toDomainTvShows(),
        totalPages = totalPages ?: 0,
        totalResults = totalResults ?: 0
    )
}
