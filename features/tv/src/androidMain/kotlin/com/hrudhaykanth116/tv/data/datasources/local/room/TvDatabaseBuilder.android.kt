package com.hrudhaykanth116.tv.data.datasources.local.room

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.mp.KoinPlatform.getKoin

actual fun getTvDatabaseBuilder(): RoomDatabase.Builder<TvDb> {
    val context = getKoin().get<android.content.Context>()

    return Room.databaseBuilder(
        context.applicationContext,
        TvDb::class.java,
        TvDb.TABLE_NAME
    ).fallbackToDestructiveMigration(dropAllTables = true)
}
