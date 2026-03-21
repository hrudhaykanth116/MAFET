package com.hrudhaykanth116.todo.data.local.room.dbs

import android.content.Context
import androidx.room.Room

/**
 * Android implementation for creating TodoDb.
 * Requires Android Context to be set before use.
 */
actual object TodoDatabaseBuilder {
    private var context: Context? = null

    fun initialize(context: Context) {
        this.context = context
    }

    actual fun build(): TodoDb {
        val appContext = context?.applicationContext
            ?: throw IllegalStateException("TodoDatabaseBuilder must be initialized with Context first")

        return Room.databaseBuilder(
            appContext,
            TodoDb::class.java,
            TodoDb.TABLE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
