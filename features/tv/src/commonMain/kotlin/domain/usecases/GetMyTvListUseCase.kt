package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domain.models.MyTv
import kotlinx.coroutines.flow.Flow

class GetMyTvListUseCase(
    private val myTvListRepository: MyTvListRepository,
) {

    operator fun invoke(): Flow<List<MyTv>> {
        return myTvListRepository.observeMyTvList()
    }

}