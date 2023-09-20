package com.hrudhaykanth116.tv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.DiscoverTvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverTvShowsRepository @Inject constructor(
    private val retroApis: RetroApis,
) {

    fun getTvShowsPagingData(genres: List<Genre>?): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                DiscoverTvShowsRemoteDataSource(retroApis, genres)
            }
        ).flow
    }

    companion object {

        private const val TAG = "DiscoverTvShowsReposito"

        private const val PAGE_SIZE = 12
    }

}