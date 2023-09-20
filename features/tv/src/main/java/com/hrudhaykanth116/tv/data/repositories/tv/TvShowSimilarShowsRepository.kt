package com.hrudhaykanth116.tv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.TvApisService
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowSimilarShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowSimilarShowsRepository @Inject constructor(
    private val tvApisService: TvApisService
) {

    fun getTvShowsPagingData(tvShowId: Int): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowSimilarShowsRemoteDataSource(tvShowId, tvApisService)
            }
        ).flow
    }

    companion object {

        private const val TAG = "TvShowSimilarShowsRepos"

        private const val PAGE_SIZE = 12
    }

}