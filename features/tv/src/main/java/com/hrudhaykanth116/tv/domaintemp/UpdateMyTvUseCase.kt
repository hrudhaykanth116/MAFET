package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDataEntity
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMyTvUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,

    ) {

    suspend operator fun invoke(myTvDomainModel: MyTvDomainModel): DataResult<Unit> {


        myTvListRepository.updateMyTvEntity(
            myTvDomainModel.toMyTvDataEntity()
        )

        return DataResult.Success(Unit)

    }

}