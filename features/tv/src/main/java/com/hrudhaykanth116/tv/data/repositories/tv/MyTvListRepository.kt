package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyTvListRepository @Inject constructor(
    private val localDataSource: MyTvListLocalDataSource,
) {

    // TODO: Make it injectable
    private val dispatcher = Dispatchers.IO

    suspend fun observeMyTvList(): Flow<List<MyTvEntity>> {
        return withContext(dispatcher) {
            localDataSource.observeMyTvList()
        }
    }

    suspend fun getMyTvList() = withContext(dispatcher) {
        localDataSource.getMyTvList()
    }

    suspend fun insertMyTvEntity(myTvEntity: MyTvEntity) = withContext(dispatcher) {
        localDataSource.insertMyTvEntity(myTvEntity)
    }

    suspend fun updateMyTvEntity(myTvEntity: MyTvEntity) = withContext(dispatcher) {
        localDataSource.updateMyTvEntity(myTvEntity)
    }

    suspend fun deleteMyTvListItemEntity(myTvEntity: MyTvEntity) = withContext(dispatcher) {
        localDataSource.updateMyTvEntity(myTvEntity)
    }

}