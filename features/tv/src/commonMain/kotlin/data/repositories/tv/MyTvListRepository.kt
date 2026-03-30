package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import kotlinx.coroutines.flow.Flow

class MyTvListRepository(
    private val localDataSource: MyTvListLocalDataSource,
): BaseRepository() {

    fun observeMyTvList(): Flow<List<MyTvEntity>> {
        return localDataSource.observeMyTvList()
    }

    suspend fun getMyTvList(): List<MyTvEntity> {
        return localDataSource.getMyTvList()
    }

    suspend fun insertMyTvEntity(myTvEntity: MyTvEntity) {
        localDataSource.insertMyTvEntity(myTvEntity)
    }

    suspend fun updateMyTvEntity(myTvEntity: MyTvEntity) {
        localDataSource.updateMyTvEntity(myTvEntity)
    }

    suspend fun deleteMyTvListItemEntity(myTvEntity: MyTvEntity) {
        localDataSource.deleteMyTvListItemEntity(myTvEntity)
    }

    suspend fun deleteMyTv(id: Int) {
        localDataSource.deleteMyTv(id)
    }

}