package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTodoRepository : ITodoRepository {

    private val tasksFlow = MutableStateFlow<List<TodoModel>>(emptyList())
    var shouldReturnError = false

    override fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoModel>> {
        return tasksFlow.map { tasks ->
            var filtered = tasks
            if (!search.isNullOrBlank()) {
                filtered = filtered.filter {
                    it.title.contains(search, ignoreCase = true) ||
                            it.description.contains(search, ignoreCase = true)
                }
            }
            if (!category.isNullOrBlank()) {
                filtered = filtered.filter { it.category.key == category }
            }
            when (sort) {
                TodoDefaults.SORT_BY_PRIORITY -> filtered.sortedByDescending { it.priority }
                else -> filtered
            }
        }
    }

    override suspend fun getTodoTask(id: String): DomainResult<TodoModel> {
        if (shouldReturnError) return DomainResult.Error(DomainError.Unknown())
        val task = tasksFlow.value.find { it.id == id }
        return if (task != null) {
            DomainResult.Success(task)
        } else {
            DomainResult.Error(DomainError.NotFound())
        }
    }

    override suspend fun createTodoTask(todoModel: TodoModel): DomainResult<Unit> {
        if (shouldReturnError) return DomainResult.Error(DomainError.Unknown())
        tasksFlow.value += todoModel
        return DomainResult.Success(Unit)
    }

    override suspend fun updateTodoTask(todoModel: TodoModel): DomainResult<Unit> {
        if (shouldReturnError) return DomainResult.Error(DomainError.Unknown())
        val exists = tasksFlow.value.any { it.id == todoModel.id }
        if (!exists) return DomainResult.Error(DomainError.NotFound())
        tasksFlow.value = tasksFlow.value.map { if (it.id == todoModel.id) todoModel else it }
        return DomainResult.Success(Unit)
    }

    override suspend fun deleteTasks(taskId: List<String>): DomainResult<Unit> {
        if (shouldReturnError) return DomainResult.Error(DomainError.Unknown())
        tasksFlow.value = tasksFlow.value.filter { it.id !in taskId }
        return DomainResult.Success(Unit)
    }

    override suspend fun deleteAllTasks(): DomainResult<Unit> {
        if (shouldReturnError) return DomainResult.Error(DomainError.Unknown())
        tasksFlow.value = emptyList()
        return DomainResult.Success(Unit)
    }

    fun addTask(task: TodoModel) {
        tasksFlow.value += task
    }

    fun getTasks(): List<TodoModel> = tasksFlow.value
}
