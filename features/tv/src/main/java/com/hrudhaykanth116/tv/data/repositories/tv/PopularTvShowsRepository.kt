package com.hrudhaykanth116.tv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.PopularTvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.coroutines.flow.Flow

class PopularTvShowsRepository(
    private val tmdbApiService: TmdbApiServiceKtor,
) {

    fun getTvShows(): Flow<PagingData<TvShowData>> {
        Log.d(TAG, "getTvShows: ")
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE,
//            maxSize = MAX_SIZE,
//            prefetchDistance = PRE_FETCH_DISTANCE,
//            jumpThreshold = JUMP_THRESHOLD
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                PopularTvShowsRemoteDataSource(tmdbApiService)
            }
        ).flow
    }

    companion object {

        private const val TAG = "PopularTvShowsRepositor"

        private const val PAGE_SIZE = 5
        private const val INITIAL_LOAD_SIZE = 2 * PAGE_SIZE
        private const val PRE_FETCH_DISTANCE = PAGE_SIZE
        private const val MAX_SIZE = PAGE_SIZE + (2 * PRE_FETCH_DISTANCE)
        private const val JUMP_THRESHOLD = Int.MIN_VALUE
    }

}