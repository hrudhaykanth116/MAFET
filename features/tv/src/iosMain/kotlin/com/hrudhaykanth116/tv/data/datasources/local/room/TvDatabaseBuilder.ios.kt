package com.hrudhaykanth116.tv.data.datasources.local.room

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun getTvDatabaseBuilder(): RoomDatabase.Builder<TvDb> {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    val dbPath = documentDirectory?.path + "/${TvDb.TABLE_NAME}"

    return Room.databaseBuilder<TvDb>(
        name = dbPath,
    )
        .setDriver(BundledSQLiteDriver())
}
