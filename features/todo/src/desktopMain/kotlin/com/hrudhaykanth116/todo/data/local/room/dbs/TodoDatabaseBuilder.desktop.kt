package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.Room
import java.io.File

/**
 * Desktop implementation for creating TodoDb.
 * Uses file-based database in user's home directory.
 */
actual object TodoDatabaseBuilder {
    actual fun build(): TodoDb {
        val dbFile = File(System.getProperty("user.home"), TodoDb.TABLE_NAME)

        return Room.databaseBuilder<TodoDb>(
            name = dbFile.absolutePath,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
