package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository

class DeleteMyTvUseCase(
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(id: Int): DomainResult<Unit> {
        myTvListRepository.deleteMyTv(id)
        return DomainResult.Success(Unit)
    }

}