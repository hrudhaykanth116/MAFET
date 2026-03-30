package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants

class AddMyTvUseCase(
    private val myTvListRepository: MyTvListRepository,
    private val tvShowsRepository: TvShowsRepository,

) {

    suspend operator fun invoke(id: Int): DomainResult<Unit>{

        val tvShowDetails: DomainResult<TvShowDetails> = tvShowsRepository.getTvShowDetails(id)

        when (tvShowDetails) {
            is DomainResult.Error -> {
                return tvShowDetails
            }
            is DomainResult.Success -> {
                val data = tvShowDetails.data

                val myTvEntity = MyTvEntity(
                    id = data.id,
                    name = data.name.replaceIfBlank("- -"),
                    lastWatchedSeason = null,
                    lastWatchedEpisode = null,
                    lastWatchedTime = null,
                    imgSource = BaseUrlConstants.IMAGES_BASE_URL + data.posterPath
                )

                myTvListRepository.insertMyTvEntity(myTvEntity)

                return DomainResult.Success(Unit)

            }
        }




    }

}