package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toDomainModel
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMyTvListUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,
) {

    operator fun invoke(): Flow<List<MyTvDomainModel>> {

        val result = myTvListRepository.observeMyTvList().map {
            it.toDomainModel()
        }

        return result

    }

}