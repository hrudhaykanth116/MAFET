package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTvDetailsUseCase @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    private val tvShowsRepository: TvShowsRepository,
) {


    suspend operator fun invoke(tvShowId: Int): RepoResultWrapper<TvShowDetails> = coroutineScope {


        val tvShowDetailsDeferred: Deferred<RepoResultWrapper<TvShowDetails>> = async {
            tvShowsRepository.getTvShowDetails(tvShowId)
        }
        val tvImagesDeferred: Deferred<RepoResultWrapper<GetTvImagesResponse>> = async {
            tvShowsRepository.getTvImages(tvShowId)
        }
        val tvShowImages = tvImagesDeferred.await()

        val tvShowDetails = tvShowDetailsDeferred.await()

        return@coroutineScope tvShowDetails


    }


}