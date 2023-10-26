package com.hrudhaykanth116.tv.data.datasources.local

import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyTvListLocalDataSource @Inject constructor(
    private val dao: MyTvListDao
) {

    fun observeMyTvList(): Flow<List<MyTvEntity>> {
        return dao.observeMyTvList()
    }

    suspend fun getMyTvList() = dao.getMyTvList()

    suspend fun insertMyTvEntity(myTvEntity: MyTvEntity){
        dao.insert(myTvEntity)
    }

    suspend fun updateMyTvEntity(myTvEntity: MyTvEntity){
        dao.update(myTvEntity)
    }

    suspend fun deleteMyTvListItemEntity(myTvEntity: MyTvEntity){
        dao.delete(myTvEntity)
    }

    suspend fun deleteMyTv(id: Int){
        dao.deleteById(id)
    }


}