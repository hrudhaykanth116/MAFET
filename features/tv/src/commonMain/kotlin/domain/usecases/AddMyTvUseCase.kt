package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import com.hrudhaykanth116.tv.domain.models.MyTv
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants

class AddMyTvUseCase(
    private val myTvListRepository: IMyTvListRepository,
    private val tvShowsRepository: ITvShowsRepository,
) {

    suspend operator fun invoke(id: Int): DomainResult<Unit> {

        val tvShowDetails: DomainResult<TvShowDetail> = tvShowsRepository.getTvShowDetails(id)

        when (tvShowDetails) {
            is DomainResult.Error -> {
                return tvShowDetails
            }
            is DomainResult.Success -> {
                val data = tvShowDetails.data

                val myTv = MyTv(
                    id = data.id,
                    name = data.name.replaceIfBlank("- -"),
                    lastWatchedSeason = null,
                    lastWatchedEpisode = null,
                    lastWatchedTime = null,
                    imgSource = BaseUrlConstants.IMAGES_BASE_URL + data.posterPath
                )

                myTvListRepository.insertMyTv(myTv)

                return DomainResult.Success(Unit)
            }
        }
    }

}