package com.hrudhaykanth116.tv.domain.models

data class TvShowPagedResult(
    val page: Int,
    val tvShows: List<TvShow>,
    val totalPages: Int,
    val totalResults: Int
)
