package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

data class TvDetailsResult(
    val tvShowDetail: TvShowDetail,
    val isBookmarked: Boolean,
)

class GetTvDetailsUseCase(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    private val tvShowsRepository: ITvShowsRepository,
    private val isTvBookmarkedUseCase: IsTvBookmarkedUseCase,
) {

    suspend operator fun invoke(tvShowId: Int): DomainResult<TvDetailsResult> = coroutineScope {

        val tvShowDetailsDeferred: Deferred<DomainResult<TvShowDetail>> = async {
            tvShowsRepository.getTvShowDetails(tvShowId)
        }
        val tvImagesDeferred: Deferred<DomainResult<GetTvImagesResponse>> = async {
            tvShowsRepository.getTvImages(tvShowId)
        }
        val isBookmarkedDeferred = async {
            isTvBookmarkedUseCase(tvShowId)
        }

        val tvShowImages = tvImagesDeferred.await()
        val tvShowDetails = tvShowDetailsDeferred.await()
        val isBookmarked = isBookmarkedDeferred.await()

        return@coroutineScope when (tvShowDetails) {
            is DomainResult.Error -> tvShowDetails
            is DomainResult.Success -> DomainResult.Success(
                TvDetailsResult(
                    tvShowDetail = tvShowDetails.data,
                    isBookmarked = isBookmarked,
                )
            )
        }
    }

}