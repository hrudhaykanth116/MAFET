package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity

@Database(
    entities = [TodoTaskDbEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TodoDb: RoomDatabase() {

    abstract fun todoTasksDao(): TodoTasksDao

    companion object{
        const val TABLE_NAME = "todo.db"
    }

}