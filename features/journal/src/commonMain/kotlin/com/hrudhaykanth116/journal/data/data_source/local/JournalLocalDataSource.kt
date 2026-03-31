package com.hrudhaykanth116.journal.data.data_source.local

import com.hrudhaykanth116.journal.data.local.room.dao.JournalEntriesDao
import com.hrudhaykanth116.journal.data.local.room.tables.JournalEntryDbEntity
import kotlinx.coroutines.flow.Flow

class JournalLocalDataSource(
    private val journalEntriesDao: JournalEntriesDao
) : IJournalLocalDataSource {

    override fun observeEntries(search: String?): Flow<List<JournalEntryDbEntity>> {
        return journalEntriesDao.observeEntries(search)
    }

    override suspend fun getEntry(id: String): JournalEntryDbEntity? {
        return journalEntriesDao.getEntryById(id)
    }

    override suspend fun createEntry(entry: JournalEntryDbEntity) {
        journalEntriesDao.insertOrUpdate(entry)
    }

    override suspend fun updateEntry(entry: JournalEntryDbEntity) {
        journalEntriesDao.insertOrUpdate(entry)
    }

    override suspend fun deleteEntry(id: String) {
        journalEntriesDao.deleteEntryById(id)
    }

    override suspend fun deleteAllEntries() {
        journalEntriesDao.deleteAllEntries()
    }

    override suspend fun markForDeletion(id: String) {
        journalEntriesDao.markForDeletion(id)
    }

    override suspend fun updateSyncStatus(id: String, status: String) {
        journalEntriesDao.updateSyncStatus(id, status)
    }
}
