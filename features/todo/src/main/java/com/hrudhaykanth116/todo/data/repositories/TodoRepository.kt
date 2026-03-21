package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.data.RepoResultWrapper
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

    override suspend fun getTodoTask(id: String): RepoResultWrapper<TodoModel> =
        withContext(dispatcher) {
            val todoEntity = todoLocalDataSource.getTodoTask(id)
            if (todoEntity == null) {
                RepoResultWrapper.Error(ErrorState.NotFound)
            } else {
                RepoResultWrapper.Success(todoEntity.toDomain())
            }
        }

    override suspend fun createTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit> =
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
                RepoResultWrapper.Success(Unit)
            } catch (e: Exception) {
                RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
            }
        }

    override suspend fun updateTodoTask(todoModel: TodoModel): RepoResultWrapper<Unit> =
        withContext(dispatcher) {
            try {
                val existing = todoLocalDataSource.getTodoTask(todoModel.id)
                if (existing == null) {
                    return@withContext RepoResultWrapper.Error(ErrorState.NotFound)
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
                RepoResultWrapper.Success(Unit)
            } catch (e: Exception) {
                RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
            }
        }

    override suspend fun deleteTasks(taskId: List<String>): RepoResultWrapper<Unit> =
        withContext(dispatcher) {
            try {
                if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    todoLocalDataSource.deleteTasks(taskId)
                } else {
                    todoLocalDataSource.markForDeletion(taskId)
                }
                RepoResultWrapper.Success(Unit)
            } catch (e: Exception) {
                RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
            }
        }

    override suspend fun deleteAllTasks(): RepoResultWrapper<Unit> = withContext(dispatcher) {
        try {
            todoLocalDataSource.deleteAllTasks()
            RepoResultWrapper.Success(Unit)
        } catch (e: Exception) {
            RepoResultWrapper.Error(ErrorState.SomethingWentWrong)
        }
    }
}