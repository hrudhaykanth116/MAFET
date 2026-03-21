package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow

class TodoLocalDataSource(
    private val todoTasksDao: TodoTasksDao,
) : ITodoLocalDataSource {

    override fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoTaskDbEntity>> {
        return todoTasksDao.getTasks(search, category, sort)
    }

    override suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        return todoTasksDao.getTaskById(id)
    }

    override suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        todoTasksDao.insertOrUpdate(todoTaskDbEntity)
    }

    override suspend fun updateTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        todoTasksDao.insertOrUpdate(todoTaskDbEntity)
    }

    override suspend fun deleteTasks(taskId: List<String>) {
        todoTasksDao.deleteTasksByIds(taskId)
    }

    override suspend fun deleteAllTasks() {
        todoTasksDao.deleteTasks()
    }

    override suspend fun getPendingTasks(): List<TodoTaskDbEntity> {
        return todoTasksDao.getPendingTasks()
    }

    override fun observePendingCount(): Flow<Int> {
        return todoTasksDao.observePendingCount()
    }

    override suspend fun updateSyncStatus(taskId: String, status: String) {
        todoTasksDao.updateSyncStatus(taskId, status)
    }

    override suspend fun markForDeletion(taskIds: List<String>) {
        todoTasksDao.markForDeletion(taskIds)
    }

    override suspend fun deleteSyncedTasks(taskIds: List<String>) {
        todoTasksDao.deleteSyncedTasks(taskIds)
    }
}