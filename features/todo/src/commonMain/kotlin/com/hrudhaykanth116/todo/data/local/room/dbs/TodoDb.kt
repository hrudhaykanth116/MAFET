package com.hrudhaykanth116.todo.data.local.room.dbs

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity

@Database(
    entities = [TodoTaskDbEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(TodoDbConstructor::class)
abstract class TodoDb: RoomDatabase() {

    abstract fun todoTasksDao(): TodoTasksDao

    companion object{
        const val TABLE_NAME = "todo.db"
    }

}

expect object TodoDbConstructor : RoomDatabaseConstructor<TodoDb> {
    override fun initialize(): TodoDb
}