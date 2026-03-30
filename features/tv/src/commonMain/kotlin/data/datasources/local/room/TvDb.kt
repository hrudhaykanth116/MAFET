package com.hrudhaykanth116.tv.data.datasources.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity

@Database(
    entities = [MyTvEntity::class],
    version = 2,
    exportSchema = true
)
@ConstructedBy(TvDbConstructor::class)
abstract class TvDb: RoomDatabase() {

    abstract fun myTvListDao(): MyTvListDao

    companion object{
        const val TABLE_NAME = "tv.db"
    }

}

expect object TvDbConstructor : RoomDatabaseConstructor<TvDb> {
    override fun initialize(): TvDb
}