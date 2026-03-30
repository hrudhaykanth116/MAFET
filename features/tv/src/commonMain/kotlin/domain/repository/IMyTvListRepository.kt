package com.hrudhaykanth116.tv.domain.repository

import com.hrudhaykanth116.tv.domain.models.MyTv
import kotlinx.coroutines.flow.Flow

interface IMyTvListRepository {
    fun observeMyTvList(): Flow<List<MyTv>>
    suspend fun getMyTvList(): List<MyTv>
    suspend fun insertMyTv(myTv: MyTv)
    suspend fun updateMyTv(myTv: MyTv)
    suspend fun deleteMyTvListItem(myTv: MyTv)
    suspend fun deleteMyTv(id: Int)
}
