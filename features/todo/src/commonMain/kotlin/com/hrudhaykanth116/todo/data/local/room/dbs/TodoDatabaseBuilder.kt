package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.RoomDatabase

/**
 * Expect declaration for creating TodoDb instance.
 * Actual implementations are platform-specific.
 */
expect object TodoDatabaseBuilder {
    fun build(): TodoDb
}
