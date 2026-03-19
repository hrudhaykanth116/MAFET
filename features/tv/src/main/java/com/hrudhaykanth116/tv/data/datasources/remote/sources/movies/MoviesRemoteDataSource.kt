package com.hrudhaykanth116.tv.data.datasources.remote.sources.movies

import com.hrudhaykanth116.core.network.NetworkDataSource
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.PopularMoviesResponse

class MoviesRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor
) : NetworkDataSource() {

    suspend fun getPopularMoviesList(pageId: Int): ApiResultWrapper<PopularMoviesResponse> =
        getResult {
            tmdbApiService.getPopularMoviesList(pageId)
        }

}