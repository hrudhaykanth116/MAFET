package com.hrudhaykanth116.tv.data.repositories.movies

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.PopularMoviesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.movies.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) {

    suspend fun getPopularMoviesList(pageId: Int): ApiResultWrapper<PopularMoviesResponse> {
        return moviesRemoteDataSource.getPopularMoviesList(pageId)
    }

}