package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
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
): BaseRepository() {

    fun observeMyTvList(): Flow<List<MyTvEntity>> {
            return localDataSource.observeMyTvList()
    }

    suspend fun getMyTvList(): List<MyTvEntity> = getLocalResult{
        localDataSource.getMyTvList()
    }

    suspend fun insertMyTvEntity(myTvEntity: MyTvEntity) = getLocalResult {
        localDataSource.insertMyTvEntity(myTvEntity)
    }

    suspend fun updateMyTvEntity(myTvEntity: MyTvEntity) = getLocalResult {
        localDataSource.updateMyTvEntity(myTvEntity)
    }

    suspend fun deleteMyTvListItemEntity(myTvEntity: MyTvEntity) = getLocalResult {
        localDataSource.updateMyTvEntity(myTvEntity)
    }

    suspend fun deleteMyTv(id: Int) = getLocalResult{
        localDataSource.deleteMyTv(id)
    }

}