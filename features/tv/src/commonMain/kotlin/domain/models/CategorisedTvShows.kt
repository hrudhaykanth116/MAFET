package com.hrudhaykanth116.tv.domain.models

data class CategorisedTvShows(
    val popular: List<TvShow>,
    val topRated: List<TvShow>,
    val airingToday: List<TvShow>,
    val trending: List<TvShow>
)
