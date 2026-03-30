package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow

interface ITodoLocalDataSource {

    fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoTaskDbEntity>>

    suspend fun getTodoTask(id: String): TodoTaskDbEntity?

    suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity)

    suspend fun updateTodoTask(todoTaskDbEntity: TodoTaskDbEntity)

    suspend fun deleteTasks(taskId: List<String>)

    suspend fun deleteAllTasks()

    suspend fun getPendingTasks(): List<TodoTaskDbEntity>

    fun observePendingCount(): Flow<Int>

    suspend fun updateSyncStatus(taskId: String, status: String)

    suspend fun markForDeletion(taskIds: List<String>)

    suspend fun deleteSyncedTasks(taskIds: List<String>)
} 