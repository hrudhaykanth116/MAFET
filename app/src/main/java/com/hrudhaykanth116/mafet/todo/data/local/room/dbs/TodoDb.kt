package com.hrudhaykanth116.mafet.todo.data.local.room.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrudhaykanth116.mafet.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask

@Database(
    entities = [TodoTask::class],
    version = 1,
    exportSchema = false,
    // autoMigrations = [
    //     AutoMigration(from = 1, to = 2)
    // ],
)
abstract class TodoDb: RoomDatabase() {

    abstract fun todoTasksDao(): TodoTasksDao

    companion object{
        const val TABLE_NAME = "todo.db"
    }

}