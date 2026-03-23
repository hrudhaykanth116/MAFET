package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.mp.KoinPlatform.getKoin

/**
 * Android implementation for creating TodoDb.
 * Gets Context from Koin.
 */
actual fun getDatabaseBuilder(): RoomDatabase.Builder<TodoDb> {
    val context = getKoin().get<android.content.Context>()

    return Room.databaseBuilder(
        context.applicationContext,
        TodoDb::class.java,
        TodoDb.TABLE_NAME
    ).fallbackToDestructiveMigration(dropAllTables = true)
}
