package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import java.io.IOException
import kotlin.random.Random

/**
 * Data source which is used to load data initially and when new data is required.
 */
class PopularTvShowsRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor,
): PagingSource<Int, TvShowData>() {

    private var initialPageId = Random.nextInt(1, 20)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val currentKey = params.key ?: initialPageId

        Log.d(TAG, "load: currentKey: $currentKey")

        return try {
            val result = tmdbApiService.getPopularTvShows(currentKey)

            if (result.isSuccess) {
                val tvShowsList = result.getOrNull()?.tvShowsList ?: emptyList()

                LoadResult.Page(
                    data = tvShowsList,
                    prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                    nextKey = currentKey + 1
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }

        } catch (exception: IOException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        val refreshKey = Random.nextInt(1, 20)
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    companion object{
        private const val TAG = "PopularTvShowsDataSourc"
    }

}