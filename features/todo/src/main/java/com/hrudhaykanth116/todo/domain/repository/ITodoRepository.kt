package com.hrudhaykanth116.todo.domain.repository

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoParams
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {

    suspend fun getTodoTask(): List<TodoModel>

    fun getTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoModel>>

    fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoModel>>

    suspend fun getTodoTask(id: String): TodoModel?

    suspend fun createTodoTask(params: CreateTodoParams): DataResult<String>

    suspend fun deleteTasks(taskId: List<String>)

    suspend fun deleteAllTasks()
}