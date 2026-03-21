package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeLocal : ITodoLocalDataSource {

    private val tasksFlow = MutableStateFlow<List<TodoTaskDbEntity>>(emptyList())
    val tasks: List<TodoTaskDbEntity>
        get() = tasksFlow.value

    override fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoTaskDbEntity>> {
        return tasksFlow.map { filterAndSortTasks(it, search, category, sort) }
    }

    override suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        return tasksFlow.value.find { it.id == id }
    }

    override suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        tasksFlow.value += todoTaskDbEntity
    }

    override suspend fun updateTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        val updated = tasksFlow.value.map {
            if (it.id == todoTaskDbEntity.id) todoTaskDbEntity else it
        }
        tasksFlow.value = updated
    }

    override suspend fun deleteTasks(taskId: List<String>) {
        tasksFlow.value = tasksFlow.value.filter { it.id !in taskId }
    }

    override suspend fun deleteAllTasks() {
        tasksFlow.value = emptyList()
    }

    override suspend fun getPendingTasks(): List<TodoTaskDbEntity> {
        return tasksFlow.value.filter { it.syncStatus != "synced" }
    }

    override fun observePendingCount(): Flow<Int> {
        return tasksFlow.map { list -> list.count { it.syncStatus != "synced" } }
    }

    override suspend fun updateSyncStatus(taskId: String, status: String) {
        tasksFlow.value = tasksFlow.value.map {
            if (it.id == taskId) it.copy(syncStatus = status) else it
        }
    }

    override suspend fun markForDeletion(taskIds: List<String>) {
        tasksFlow.value = tasksFlow.value.map {
            if (it.id in taskIds) it.copy(syncStatus = "pending_delete") else it
        }
    }

    override suspend fun deleteSyncedTasks(taskIds: List<String>) {
        tasksFlow.value = tasksFlow.value.filter {
            !(it.id in taskIds && it.syncStatus == "synced")
        }
    }

    fun addTask(task: TodoTaskDbEntity) {
        tasksFlow.value = tasksFlow.value + task
    }

    private fun filterAndSortTasks(
        tasks: List<TodoTaskDbEntity>,
        search: String?,
        category: String?,
        sort: String
    ): List<TodoTaskDbEntity> {
        var filtered = tasks
        if (!search.isNullOrBlank()) {
            filtered = filtered.filter {
                it.title.contains(search, ignoreCase = true) ||
                        it.description.contains(search, ignoreCase = true)
            }
        }
        if (!category.isNullOrBlank()) {
            filtered = filtered.filter { it.category == category }
        }
        return when (sort) {
            "priority" -> filtered.sortedByDescending { it.priority }
            "timeUpdated" -> filtered.sortedByDescending { it.timeUpdated }
            else -> filtered
        }
    }
}
