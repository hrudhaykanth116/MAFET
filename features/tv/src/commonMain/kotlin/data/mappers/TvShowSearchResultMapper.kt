package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.domain.models.TvShowSearchResult

fun TvShowSearchResults.toDomain(): TvShowSearchResult {
    return TvShowSearchResult(
        page = page ?: 1,
        tvShows = tvShowDataList?.filterNotNull()?.toDomainTvShows().orEmpty(),
        totalPages = totalPages ?: 0,
        totalResults = totalResults ?: 0
    )
}
