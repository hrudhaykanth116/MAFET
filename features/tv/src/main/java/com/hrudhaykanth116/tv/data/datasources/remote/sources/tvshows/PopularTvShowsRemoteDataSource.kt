package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.random.Random

/**
 * Data source which is used to load data initially and when new data is required.
 */
class PopularTvShowsRemoteDataSource constructor(
    private val retroApis: RetroApis,
) : PagingSource<Int, TvShowData>() {

    private var initialPageId = Random.nextInt(1, 20)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val currentKey = params.key ?: initialPageId

        Log.d(TAG, "load: currentKey: $currentKey")

        return try {
            val tvShowDataPagedResponse: Response<TvShowDataPagedResponse> =
                retroApis.getPopularTvShows(currentKey)
            // TODO: 12/06/21 Check if response is successful
            val tvShowsList = tvShowDataPagedResponse.body()?.tvShowsList ?: listOf()

            LoadResult.Page(
                data = tvShowsList,
                prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                nextKey = currentKey + 1
            )

        } catch (exception: IOException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
//        val refreshKey = state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
        val refreshKey = Random.nextInt(1, 20)
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    companion object {
        private const val TAG = "PopularTvShowsDataSourc"
    }

}