package com.hrudhaykanth116.journal.data.local.room.dbs

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.mp.KoinPlatform.getKoin

actual fun getDatabaseBuilder(): RoomDatabase.Builder<JournalDb> {
    val context = getKoin().get<android.content.Context>()

    return Room.databaseBuilder(
        context.applicationContext,
        JournalDb::class.java,
        JournalDb.TABLE_NAME
    )
}
