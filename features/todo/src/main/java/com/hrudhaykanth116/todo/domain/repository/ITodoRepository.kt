package com.hrudhaykanth116.todo.domain.repository

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.model.TodoModel
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

    suspend fun getTodoTask(id: String): RepoResultWrapper<TodoModel>

    suspend fun createTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit>
    suspend fun updateTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit>

    suspend fun deleteTasks(taskId: List<String>)

    suspend fun deleteAllTasks()
}