package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toDomainModel
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMyTvListUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(): DataResult<List<MyTvDomainModel>> {

        val tvShowResult: List<MyTvEntity> = myTvListRepository.getMyTvList()

        return DataResult.Success(
            tvShowResult.toDomainModel()
        )

    }

}