package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository

class DeleteMyTvUseCase(
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(id: Int): RepoResultWrapper<Unit> {
        myTvListRepository.deleteMyTv(id)
        return RepoResultWrapper.Success(Unit)
    }

}