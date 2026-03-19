package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDataEntity
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel

class UpdateMyTvUseCase(
    private val myTvListRepository: MyTvListRepository,
) {

    // hrudhay_check_list: Update only season, episode, time
    suspend operator fun invoke(myTvDomainModel: MyTvDomainModel): RepoResultWrapper<Unit> {

        myTvListRepository.updateMyTvEntity(
            myTvDomainModel.toMyTvDataEntity()
        )

        return RepoResultWrapper.Success(Unit)

    }

}