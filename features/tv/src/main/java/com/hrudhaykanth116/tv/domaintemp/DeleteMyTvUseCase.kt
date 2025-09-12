package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteMyTvUseCase @Inject constructor(
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(id: Int): RepoResultWrapper<Unit> {
        myTvListRepository.deleteMyTv(id)
        return RepoResultWrapper.Success(Unit)
    }

}