package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository
import com.hrudhaykanth116.tv.domain.models.MyTv

class UpdateMyTvUseCase(
    private val myTvListRepository: IMyTvListRepository,
) {

    suspend operator fun invoke(myTv: MyTv): DomainResult<Unit> {

        myTvListRepository.updateMyTv(myTv)

        return DomainResult.Success(Unit)

    }

}