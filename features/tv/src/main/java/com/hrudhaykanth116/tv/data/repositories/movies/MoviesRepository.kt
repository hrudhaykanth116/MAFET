package com.hrudhaykanth116.tv.data.repositories.movies

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.sources.movies.MoviesRemoteDataSource
import com.hrudhaykanth116.tv.data.models.models.PopularMoviesResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) {

    suspend fun getPopularMoviesList(pageId: Int): DataResult<PopularMoviesResponse> {
        return moviesRemoteDataSource.getPopularMoviesList(pageId)
    }

}