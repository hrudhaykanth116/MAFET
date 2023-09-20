package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDataEntity
import com.hrudhaykanth116.tv.domaintemp.mappers.toMyTvDomainModel
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddMyTvUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository
) {

    suspend operator fun invoke(myTvDomainModel: MyTvDomainModel){

        myTvListRepository.insertMyTvEntity(myTvDomainModel.toMyTvDataEntity())


    }

}