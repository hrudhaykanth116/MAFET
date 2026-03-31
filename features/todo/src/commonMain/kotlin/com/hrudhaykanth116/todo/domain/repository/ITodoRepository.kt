package com.hrudhaykanth116.todo.domain.repository

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.domain.model.TodoModel
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {

    fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoModel>>

    suspend fun getTasks(): List<TodoModel>

    suspend fun getTodoTask(id: String): DomainResult<TodoModel>

    suspend fun createTodoTask(todoModel: TodoModel): DomainResult<Unit>

    suspend fun updateTodoTask(todoModel: TodoModel): DomainResult<Unit>

    suspend fun deleteTasks(taskId: List<String>): DomainResult<Unit>

    suspend fun deleteAllTasks(): DomainResult<Unit>
}