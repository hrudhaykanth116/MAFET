package com.hrudhaykanth116.journal.data.local.room.dbs

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getDatabaseBuilder(): RoomDatabase.Builder<JournalDb> {
    val dbFile = File(System.getProperty("user.home"), JournalDb.TABLE_NAME)

    return Room.databaseBuilder<JournalDb>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}
