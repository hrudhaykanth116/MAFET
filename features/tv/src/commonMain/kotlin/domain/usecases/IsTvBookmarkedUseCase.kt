package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository

class IsTvBookmarkedUseCase(
    private val myTvListRepository: IMyTvListRepository,
) {

    suspend operator fun invoke(tvId: Int): Boolean {
        val myTvList = myTvListRepository.getMyTvList()
        return myTvList.any { it.id == tvId }
    }

}
