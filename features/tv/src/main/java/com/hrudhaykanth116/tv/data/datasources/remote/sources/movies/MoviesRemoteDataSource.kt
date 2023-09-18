package com.hrudhaykanth116.tv.data.datasources.remote.sources.movies

import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    val retroApis: RetroApis,
) : NetworkDataSource() {

    suspend fun getPopularMoviesList(pageId: Int) =
        getResult {
            retroApis.getPopularMoviesList(pageId)
        }

}