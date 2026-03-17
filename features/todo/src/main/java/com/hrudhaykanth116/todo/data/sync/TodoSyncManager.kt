package com.hrudhaykanth116.todo.data.sync

import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.mappers.toDomain
import com.hrudhaykanth116.todo.domain.model.SyncStatus
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.sync.ITodoSyncManager
import com.hrudhaykanth116.todo.domain.sync.SyncResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class TodoSyncManager(
    private val localDataSource: ITodoLocalDataSource,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher,
) : ITodoSyncManager {

    override fun observePendingCount(): Flow<Int> {
        return localDataSource.observePendingCount()
    }

    override suspend fun getPendingTasks(): List<TodoModel> = withContext(dispatcher) {
        localDataSource.getPendingTasks().map { it.toDomain() }
    }

    override suspend fun markAsSynced(taskId: String) = withContext(dispatcher) {
        localDataSource.updateSyncStatus(taskId, SyncStatus.SYNCED.key)
    }

    override suspend fun syncPendingTasks(): SyncResult = withContext(dispatcher) {
        if (!networkMonitor.internetAvailabilityStateFlow.first()) {
            return@withContext SyncResult.NoNetwork
        }

        val pendingTasks = localDataSource.getPendingTasks()
        if (pendingTasks.isEmpty()) {
            return@withContext SyncResult.NothingToSync
        }

        var syncedCount = 0
        var failedCount = 0

        pendingTasks.forEach { task ->
            val success = syncTask(task.toDomain())
            if (success) {
                syncedCount++
            } else {
                failedCount++
            }
        }

        when {
            failedCount == 0 -> SyncResult.Success(syncedCount)
            syncedCount == 0 -> SyncResult.Failure("All sync operations failed")
            else -> SyncResult.PartialSuccess(syncedCount, failedCount)
        }
    }

    private suspend fun syncTask(task: TodoModel): Boolean {
        return try {
            when (task.syncStatus) {
                SyncStatus.PENDING_CREATE -> syncCreate(task)
                SyncStatus.PENDING_UPDATE -> syncUpdate(task)
                SyncStatus.PENDING_DELETE -> syncDelete(task)
                SyncStatus.SYNCED -> true
            }
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun syncCreate(task: TodoModel): Boolean {
        localDataSource.updateSyncStatus(task.id, SyncStatus.SYNCED.key)
        return true
    }

    private suspend fun syncUpdate(task: TodoModel): Boolean {
        localDataSource.updateSyncStatus(task.id, SyncStatus.SYNCED.key)
        return true
    }

    private suspend fun syncDelete(task: TodoModel): Boolean {
        localDataSource.deleteSyncedTasks(listOf(task.id))
        return true
    }

    override suspend fun scheduleSyncWork() {
        // todo: integrate workmanager
    }

    companion object {
        const val SYNC_WORK_NAME = "todo_sync_work"
    }
}
