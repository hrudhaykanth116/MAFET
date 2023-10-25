package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddMyTvUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,
    private val tvShowsRepository: TvShowsRepository,

) {

    suspend operator fun invoke(id: Int): DataResult<Unit>{

        val tvShowDetails: DataResult<TvShowDetails> = tvShowsRepository.getTvShowDetails(id)

        when (tvShowDetails) {
            is DataResult.Error -> {
                // TODO: Handle error
                return DataResult.Error()
            }
            is DataResult.Success -> {
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

                return DataResult.Success(Unit)

            }
        }




    }

}