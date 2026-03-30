package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.mappers.toDomain
import com.hrudhaykanth116.tv.data.mappers.toDomainMyTvList
import com.hrudhaykanth116.tv.data.mappers.toEntity
import com.hrudhaykanth116.tv.domain.models.MyTv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyTvListRepository(
    private val localDataSource: MyTvListLocalDataSource,
) {

    fun observeMyTvList(): Flow<List<MyTv>> {
        return localDataSource.observeMyTvList().map { it.toDomainMyTvList() }
    }

    suspend fun getMyTvList(): List<MyTv> {
        return localDataSource.getMyTvList().toDomainMyTvList()
    }

    suspend fun insertMyTv(myTv: MyTv) {
        localDataSource.insertMyTvEntity(myTv.toEntity())
    }

    suspend fun updateMyTv(myTv: MyTv) {
        localDataSource.updateMyTvEntity(myTv.toEntity())
    }

    suspend fun deleteMyTvListItem(myTv: MyTv) {
        localDataSource.deleteMyTvListItemEntity(myTv.toEntity())
    }

    suspend fun deleteMyTv(id: Int) {
        localDataSource.deleteMyTv(id)
    }

}