package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.mappers.toDomain
import com.hrudhaykanth116.todo.data.mappers.toLocal
import com.hrudhaykanth116.todo.domain.model.SyncStatus
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TodoRepository(
    private val todoLocalDataSource: ITodoLocalDataSource,
    private val timeProvider: TimeProvider,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher,
) : ITodoRepository {

    override fun observeTasks(
        search: String?,
        category: String?,
        sort: String
    ): Flow<List<TodoModel>> =
        todoLocalDataSource.observeTasks(search, category, sort)
            .map { list ->
                list.filter { it.syncStatus != SyncStatus.PENDING_DELETE.key }
                    .map { it.toDomain() }
            }

    override suspend fun getTasks(): List<TodoModel> = withContext(dispatcher) {
        todoLocalDataSource.observeTasks(null, null, "priority")
            .first()
            .filter { it.syncStatus != SyncStatus.PENDING_DELETE.key }
            .map { it.toDomain() }
    }

    override suspend fun getTodoTask(id: String): DomainResult<TodoModel> =
        withContext(dispatcher) {
            val todoEntity = todoLocalDataSource.getTodoTask(id)
            if (todoEntity == null) {
                DomainResult.Error(DomainError.NotFound("Task not found"))
            } else {
                DomainResult.Success(todoEntity.toDomain())
            }
        }

    override suspend fun createTodoTask(todoModel: TodoModel): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                val syncStatus = if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    SyncStatus.SYNCED
                } else {
                    SyncStatus.PENDING_CREATE
                }
                val modelWithSyncStatus = todoModel.copy(syncStatus = syncStatus)
                val local = modelWithSyncStatus.toLocal(timeProvider.currentTimeMillis())
                todoLocalDataSource.createTodoTask(local)
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to create task"))
            }
        }

    override suspend fun updateTodoTask(todoModel: TodoModel): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                val existing = todoLocalDataSource.getTodoTask(todoModel.id)
                if (existing == null) {
                    return@withContext DomainResult.Error(DomainError.NotFound("Task not found"))
                }
                val syncStatus = if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    SyncStatus.SYNCED
                } else {
                    if (existing.syncStatus == SyncStatus.PENDING_CREATE.key) {
                        SyncStatus.PENDING_CREATE
                    } else {
                        SyncStatus.PENDING_UPDATE
                    }
                }
                val modelWithSyncStatus = todoModel.copy(syncStatus = syncStatus)
                val local = modelWithSyncStatus.toLocal(timeProvider.currentTimeMillis())
                todoLocalDataSource.updateTodoTask(local)
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to update task"))
            }
        }

    override suspend fun deleteTasks(taskId: List<String>): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    todoLocalDataSource.deleteTasks(taskId)
                } else {
                    todoLocalDataSource.markForDeletion(taskId)
                }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to delete tasks"))
            }
        }

    override suspend fun deleteAllTasks(): DomainResult<Unit> = withContext(dispatcher) {
        try {
            todoLocalDataSource.deleteAllTasks()
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            DomainResult.Error(DomainError.Unknown(e, "Failed to delete all tasks"))
        }
    }
}