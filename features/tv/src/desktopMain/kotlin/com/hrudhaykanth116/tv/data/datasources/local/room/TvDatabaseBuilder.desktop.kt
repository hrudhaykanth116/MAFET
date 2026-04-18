package com.hrudhaykanth116.tv.data.datasources.local.room

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getTvDatabaseBuilder(): RoomDatabase.Builder<TvDb> {
    val dbFile = File(System.getProperty("user.home"), TvDb.TABLE_NAME)

    return Room.databaseBuilder<TvDb>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}
