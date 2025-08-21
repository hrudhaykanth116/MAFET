package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTvDetailsUseCase @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
) {


    suspend operator fun invoke(tvShowId: Int): DataResult<TvShowDetails> = coroutineScope {


        val tvShowDetailsDeferred: Deferred<DataResult<TvShowDetails>> = async {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }
        val tvImagesDeferred: Deferred<DataResult<GetTvImagesResponse>> = async(start = CoroutineStart.LAZY) {
            tvShowsRemoteDataSource.getTvImages(tvShowId)
        }

        val tvShowDetails = tvShowDetailsDeferred.await()
        val tvShowImages = tvImagesDeferred.await()

        return@coroutineScope tvShowDetails


    }


}