package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocal : ITodoLocalDataSource {
    val tasks = mutableListOf<TodoTaskDbEntity>()
    override suspend fun getTodoTasks(): List<TodoTaskDbEntity> = tasks

    override fun getTasks(search: String?, category: String?, sort: String): Flow<List<TodoTaskDbEntity>> =
        flowOf(filterAndSortTasks(search, category, sort))

    override fun getTodoTasksFlow(search: String, filterCategory: String?, sortItem: String): Flow<List<TodoTaskDbEntity>> =
        flowOf(filterAndSortTasks(search, filterCategory, sortItem))

    override suspend fun getTodoTask(id: String): TodoTaskDbEntity? = tasks.find { it.id == id }
    override suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity) { tasks.add(todoTaskDbEntity) }
    override suspend fun deleteTasks(taskId: List<String>) { tasks.removeAll { it.id in taskId } }
    override suspend fun deleteAllTasks() { tasks.clear() }

    private fun filterAndSortTasks(search: String?, category: String?, sort: String): List<TodoTaskDbEntity> {
        var filtered = tasks.toList()
        if (!search.isNullOrBlank()) {
            filtered = filtered.filter {
                it.title.contains(search, ignoreCase = true) ||
                        it.description.contains(search, ignoreCase = true)
            }
        }
        if (!category.isNullOrBlank()) {
            filtered = filtered.filter { it.category == category }
        }
        filtered = when (sort) {
            "priority" -> filtered.sortedByDescending { it.priority }
            "timeUpdated" -> filtered.sortedByDescending { it.timeUpdated }
            else -> filtered
        }
        return filtered
    }
}
