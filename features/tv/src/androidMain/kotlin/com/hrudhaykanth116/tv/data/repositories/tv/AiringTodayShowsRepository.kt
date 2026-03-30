package com.hrudhaykanth116.tv.data.repositories.tv

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.AiringTodayShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import kotlinx.coroutines.flow.Flow

class AiringTodayShowsRepository(
    private val tmdbApiService: TmdbApiServiceKtor,
) {

    fun getTvShowsPagingData(): Flow<PagingData<TvShowData>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = 2 * PAGE_SIZE
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                AiringTodayShowsRemoteDataSource(tmdbApiService)
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 12
    }

}
