package com.hrudhaykanth116.journal.data.repositories

import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.journal.data.data_source.local.IJournalLocalDataSource
import com.hrudhaykanth116.journal.data.mappers.toDomain
import com.hrudhaykanth116.journal.data.mappers.toEntity
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.model.SyncStatus
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class JournalRepository(
    private val localDataSource: IJournalLocalDataSource,
    private val timeProvider: TimeProvider,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher
) : IJournalRepository {

    override fun observeEntries(search: String?): Flow<List<JournalEntry>> {
        return localDataSource.observeEntries(search)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getEntry(id: String): DomainResult<JournalEntry> =
        withContext(dispatcher) {
            val entity = localDataSource.getEntry(id)
            if (entity == null) {
                DomainResult.Error(DomainError.NotFound("Entry not found"))
            } else {
                DomainResult.Success(entity.toDomain())
            }
        }

    override suspend fun createEntry(entry: JournalEntry): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                val syncStatus = if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    SyncStatus.SYNCED
                } else {
                    SyncStatus.PENDING_CREATE
                }
                val currentTime = timeProvider.currentTimeMillis()
                val entryWithTimestamps = entry.copy(
                    createdAt = currentTime,
                    updatedAt = currentTime,
                    syncStatus = syncStatus
                )
                localDataSource.createEntry(entryWithTimestamps.toEntity())
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to create entry"))
            }
        }

    override suspend fun updateEntry(entry: JournalEntry): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                val existing = localDataSource.getEntry(entry.id)
                if (existing == null) {
                    return@withContext DomainResult.Error(DomainError.NotFound("Entry not found"))
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
                val entryWithUpdatedTime = entry.copy(
                    createdAt = existing.createdAt,
                    updatedAt = timeProvider.currentTimeMillis(),
                    syncStatus = syncStatus
                )
                localDataSource.updateEntry(entryWithUpdatedTime.toEntity())
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to update entry"))
            }
        }

    override suspend fun deleteEntry(id: String): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                if (networkMonitor.internetAvailabilityStateFlow.first()) {
                    localDataSource.deleteEntry(id)
                } else {
                    localDataSource.markForDeletion(id)
                }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to delete entry"))
            }
        }

    override suspend fun deleteAllEntries(): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                localDataSource.deleteAllEntries()
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(e, "Failed to delete all entries"))
            }
        }
}
