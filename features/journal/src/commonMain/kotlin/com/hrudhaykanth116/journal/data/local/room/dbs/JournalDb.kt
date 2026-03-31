package com.hrudhaykanth116.journal.data.local.room.dbs

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.hrudhaykanth116.journal.data.local.room.dao.JournalEntriesDao
import com.hrudhaykanth116.journal.data.local.room.tables.JournalEntryDbEntity

@Database(
    entities = [JournalEntryDbEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(JournalDbConstructor::class)
abstract class JournalDb : RoomDatabase() {

    abstract fun journalEntriesDao(): JournalEntriesDao

    companion object {
        const val TABLE_NAME = "journal.db"
    }
}

expect object JournalDbConstructor : RoomDatabaseConstructor<JournalDb> {
    override fun initialize(): JournalDb
}
