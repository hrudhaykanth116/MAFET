package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

/**
 * Desktop implementation for creating TodoDb.
 * Uses file-based database in user's home directory.
 */
actual fun getDatabaseBuilder(): RoomDatabase.Builder<TodoDb> {
    val dbFile = File(System.getProperty("user.home"), TodoDb.TABLE_NAME)

    return Room.databaseBuilder<TodoDb>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}
