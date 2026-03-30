package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository

class DeleteMyTvUseCase(
    private val myTvListRepository: IMyTvListRepository,
) {

    suspend operator fun invoke(id: Int): DomainResult<Unit> {
        myTvListRepository.deleteMyTv(id)
        return DomainResult.Success(Unit)
    }

}