package com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlin.random.Random

class TvShowsPagingSource<T : Any>(
    private val initialPageRange: IntRange = 1..20,
    private val fetchPage: suspend (pageId: Int) -> Result<List<T>>,
) : PagingSource<Int, T>() {

    private var initialPageId = Random.nextInt(initialPageRange.first, initialPageRange.last)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentKey = params.key ?: initialPageId
        Logger.d(TAG, "load: currentKey: $currentKey")

        return try {
            val result = fetchPage(currentKey)
            if (result.isSuccess) {
                LoadResult.Page(
                    data = result.getOrNull().orEmpty(),
                    prevKey = if (currentKey == initialPageId) null else currentKey - 1,
                    nextKey = currentKey + 1,
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (exception: Exception) {
            Logger.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val refreshKey = Random.nextInt(initialPageRange.first, initialPageRange.last)
        initialPageId = refreshKey
        return refreshKey
    }

    companion object {
        private const val TAG = "TvShowsPagingSource"
    }
}
