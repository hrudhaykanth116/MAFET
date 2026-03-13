package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
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

    override suspend fun getTodoTask(id: String): RepoResultWrapper<TodoModel> {
        if (shouldReturnError) return RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        val task = tasksFlow.value.find { it.id == id }
        return if (task != null) {
            RepoResultWrapper.Success(task)
        } else {
            RepoResultWrapper.Error(ErrorState.NotFound)
        }
    }

    override suspend fun createTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit> {
        if (shouldReturnError) return RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        tasksFlow.value += todoModel
        return RepoResultWrapper.Success(Unit)
    }

    override suspend fun updateTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit> {
        if (shouldReturnError) return RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        val exists = tasksFlow.value.any { it.id == todoModel.id }
        if (!exists) return RepoResultWrapper.Error(ErrorState.NotFound)
        tasksFlow.value = tasksFlow.value.map { if (it.id == todoModel.id) todoModel else it }
        return RepoResultWrapper.Success(Unit)
    }

    override suspend fun deleteTasks(taskId: List<String>): RepoResultWrapper<Unit> {
        if (shouldReturnError) return RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        tasksFlow.value = tasksFlow.value.filter { it.id !in taskId }
        return RepoResultWrapper.Success(Unit)
    }

    override suspend fun deleteAllTasks(): RepoResultWrapper<Unit> {
        if (shouldReturnError) return RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        tasksFlow.value = emptyList()
        return RepoResultWrapper.Success(Unit)
    }

    fun addTask(task: TodoModel) {
        tasksFlow.value += task
    }

    fun getTasks(): List<TodoModel> = tasksFlow.value
}
