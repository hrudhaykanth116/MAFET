package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow

interface ITodoLocalDataSource {

    suspend fun getTodoTasks(): List<TodoTaskDbEntity>

    fun getTasks(
        search: String?, category: String?, sort: String
    ): Flow<List<TodoTaskDbEntity>>

    fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoTaskDbEntity>>

    suspend fun getTodoTask(id: String): TodoTaskDbEntity?

    suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity)

    suspend fun deleteTasks(taskId: List<String>)

    suspend fun deleteAllTasks()
} 