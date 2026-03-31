package com.hrudhaykanth116.journal.data.data_source.local

import com.hrudhaykanth116.journal.data.local.room.tables.JournalEntryDbEntity
import kotlinx.coroutines.flow.Flow

interface IJournalLocalDataSource {

    fun observeEntries(search: String?): Flow<List<JournalEntryDbEntity>>

    suspend fun getEntry(id: String): JournalEntryDbEntity?

    suspend fun createEntry(entry: JournalEntryDbEntity)

    suspend fun updateEntry(entry: JournalEntryDbEntity)

    suspend fun deleteEntry(id: String)

    suspend fun deleteAllEntries()

    suspend fun markForDeletion(id: String)

    suspend fun updateSyncStatus(id: String, status: String)
}
