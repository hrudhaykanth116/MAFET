package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDataEntity
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMyTvUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,
) {

    // TODO: Update only season, episode, time
    suspend operator fun invoke(myTvDomainModel: MyTvDomainModel): DataResult<Unit> {

        myTvListRepository.updateMyTvEntity(
            myTvDomainModel.toMyTvDataEntity()
        )

        return DataResult.Success(Unit)

    }

}