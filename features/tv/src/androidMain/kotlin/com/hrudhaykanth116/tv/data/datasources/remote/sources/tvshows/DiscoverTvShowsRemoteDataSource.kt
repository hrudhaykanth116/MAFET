package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import kotlin.random.Random

class DiscoverTvShowsRemoteDataSource(
    private val tmdbApiService: TmdbApiServiceKtor,
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
    }

    private var initialPageId = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val currentKey = params.key ?: initialPageId

        Logger.d(TAG, "load: currentKey: $currentKey")

        return try {
            val result = tmdbApiService.discoverTv(currentKey, commaSeparatedGenreIds)

            if (result.isSuccess) {
                val tvShowsList: List<TvShowData> = result.getOrNull()?.tvShowsList ?: emptyList()

                LoadResult.Page(
                    data = tvShowsList,
                    prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                    nextKey = currentKey + 1
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }

        } catch (exception: Exception) {
            Logger.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        val refreshKey = Random.nextInt(1, 10)
        Logger.d(TAG, "getRefreshKey: refreshKey: $refreshKey")
        initialPageId = refreshKey
        return refreshKey
    }

    companion object {
        private const val TAG = "DiscoverTvShowsRemoteDataSource"
    }

}