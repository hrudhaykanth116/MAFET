package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor


class TrendingTvShowsRemoteDataSource constructor(
    private val tmdbApiService: TmdbApiServiceKtor
)