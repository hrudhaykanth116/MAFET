package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository
import com.hrudhaykanth116.tv.domain.models.MyTv
import kotlinx.coroutines.flow.Flow

class GetMyTvListUseCase(
    private val myTvListRepository: IMyTvListRepository,
) {

    operator fun invoke(): Flow<List<MyTv>> {
        return myTvListRepository.observeMyTvList()
    }

}