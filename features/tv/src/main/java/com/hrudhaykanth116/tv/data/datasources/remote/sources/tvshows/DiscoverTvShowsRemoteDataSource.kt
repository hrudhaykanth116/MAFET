package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.discover.GetDiscoverTvResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.random.Random

class DiscoverTvShowsRemoteDataSource constructor(
    private val retroApis: RetroApis,
    genres: List<Genre>?,
) : PagingSource<Int, TvShowData>() {

    private val commaSeparatedGenreIds: String = run {
        if (genres.isNullOrEmpty()) {
            return@run "10759|99|16|10762|10765"
        } else {
            return@run genres.joinToString {
                "${it.id}"
            }
        }
        // genres?.joinToString {
        //     "${it.id}"
        // } ?: run{
        //     // Some default genres --> implicitly showing different genres types.
        //     "10759|99|16|10762|10765"
        // }
    }

    private var initialPageId = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val currentKey = params.key ?: initialPageId

        Log.d(TAG, "load: currentKey: $currentKey")

        return try {
            val discoverTvShowsResponse: Response<GetDiscoverTvResponse> =
                retroApis.discoverTv(currentKey, commaSeparatedGenreIds)
            val tvShowsList: List<TvShowData> =
                discoverTvShowsResponse.body()?.results ?: arrayListOf()

            // TODO: 29/05/21 Check if the response is successful

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
        /*val refreshKey = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        return refreshKey*/
        val refreshKey = Random.nextInt(1, 10)
        Log.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    companion object {
        private val TAG: String = DiscoverTvShowsRemoteDataSource::class.java.name
    }

}