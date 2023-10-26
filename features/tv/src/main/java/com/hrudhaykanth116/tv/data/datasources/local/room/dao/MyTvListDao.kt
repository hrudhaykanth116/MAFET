package com.hrudhaykanth116.tv.data.datasources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.hrudhaykanth116.core.data.local.db.BaseDao
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyTvListDao: BaseDao<MyTvEntity> {

    @Query("SELECT * FROM MyTvEntity")
    fun observeMyTvList(): Flow<List<MyTvEntity>>

    @Query("SELECT * FROM MyTvEntity")
    suspend fun getMyTvList(): List<MyTvEntity>

    @Query("DELETE FROM MyTvEntity where id = :id")
    suspend fun deleteById(id: Int)


}