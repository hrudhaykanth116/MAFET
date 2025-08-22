package com.hrudhaykanth116.tv.data.datasources.remote.models.tv

import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData

data class CategorisedTvShows(
    val popular: List<TvShowData> = emptyList(),
    val topRated: List<TvShowData> = emptyList(),
    val airingToday: List<TvShowData> = emptyList(),
    val trending: List<TvShowData> = emptyList()
)