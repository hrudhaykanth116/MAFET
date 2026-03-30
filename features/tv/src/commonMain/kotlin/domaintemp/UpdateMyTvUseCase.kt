package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDataEntity
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel

class UpdateMyTvUseCase(
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(myTvDomainModel: MyTvDomainModel): DomainResult<Unit> {

        myTvListRepository.updateMyTvEntity(
            myTvDomainModel.toMyTvDataEntity()
        )

        return DomainResult.Success(Unit)

    }

}