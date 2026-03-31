package com.hrudhaykanth116.mafet.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger
import com.hrudhaykanth116.todo.domain.sync.ITodoSyncManager
import com.hrudhaykanth116.todo.domain.sync.SyncResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val syncManager: ITodoSyncManager by inject()

    override suspend fun doWork(): Result {
        Logger.d { "TodoSyncWorker: Starting sync" }

        return when (val syncResult = syncManager.syncPendingTasks()) {
            is SyncResult.Success -> {
                Logger.d { "TodoSyncWorker: Synced ${syncResult.syncedCount} tasks" }
                Result.success()
            }
            is SyncResult.PartialSuccess -> {
                Logger.w { "TodoSyncWorker: Partial sync - ${syncResult.syncedCount} synced, ${syncResult.failedCount} failed" }
                Result.success()
            }
            is SyncResult.NoNetwork -> {
                Logger.d { "TodoSyncWorker: No network, will retry" }
                Result.retry()
            }
            is SyncResult.NothingToSync -> {
                Logger.d { "TodoSyncWorker: Nothing to sync" }
                Result.success()
            }
            is SyncResult.Failure -> {
                Logger.e { "TodoSyncWorker: Sync failed - ${syncResult.reason}" }
                if (runAttemptCount < 3) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }
        }
    }

    companion object {
        const val WORK_NAME = "todo_sync_work"
        const val PERIODIC_WORK_NAME = "todo_periodic_sync"
    }
}
