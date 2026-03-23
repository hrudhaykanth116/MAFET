package com.hrudhaykanth116.todo.data.local.room.dbs

/**
 * Expect function for creating TodoDb instance.
 * Actual implementations are platform-specific.
 */
expect fun getDatabaseBuilder(): androidx.room.RoomDatabase.Builder<TodoDb>
