package com.hrudhaykanth116.todo.domain.sync

import com.hrudhaykanth116.todo.domain.model.TodoModel
import kotlinx.coroutines.flow.Flow

interface ITodoSyncManager {

    fun observePendingCount(): Flow<Int>

    suspend fun getPendingTasks(): List<TodoModel>

    suspend fun markAsSynced(taskId: String)

    suspend fun syncPendingTasks(): SyncResult

    suspend fun scheduleSyncWork()
}

sealed class SyncResult {
    data class Success(val syncedCount: Int) : SyncResult()
    data class PartialSuccess(val syncedCount: Int, val failedCount: Int) : SyncResult()
    data class Failure(val reason: String) : SyncResult()
    data object NoNetwork : SyncResult()
    data object NothingToSync : SyncResult()
}
