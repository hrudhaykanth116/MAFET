package com.hrudhaykanth116.tv.data.datasources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity

@Database(
    entities = [MyTvEntity::class],
    version = 2,
    exportSchema = false,
    // autoMigrations = [
    //     AutoMigration(from = 1, to = 2)
    // ],
)
abstract class TvDb: RoomDatabase() {

    abstract fun myTvListDao(): MyTvListDao

    companion object{
        const val TABLE_NAME = "tv.db"
    }

}