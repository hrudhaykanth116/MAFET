package com.hrudhaykanth116.journal.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.hrudhaykanth116.core.data.local.room.BaseDao
import com.hrudhaykanth116.journal.data.local.room.tables.JournalEntryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntriesDao : BaseDao<JournalEntryDbEntity> {

    @Query("""
        SELECT * FROM JournalEntryDbEntity
        WHERE (:search IS NULL OR title LIKE '%' || :search || '%' OR body LIKE '%' || :search || '%')
        AND syncStatus != 'pending_delete'
        ORDER BY createdAt DESC
    """)
    fun observeEntries(search: String?): Flow<List<JournalEntryDbEntity>>

    @Query("SELECT * FROM JournalEntryDbEntity WHERE id = :entryId")
    suspend fun getEntryById(entryId: String): JournalEntryDbEntity?

    @Query("SELECT * FROM JournalEntryDbEntity WHERE id = :entryId")
    fun observeEntryById(entryId: String): Flow<JournalEntryDbEntity?>

    @Query("DELETE FROM JournalEntryDbEntity WHERE id = :entryId")
    suspend fun deleteEntryById(entryId: String): Int

    @Query("DELETE FROM JournalEntryDbEntity")
    suspend fun deleteAllEntries()

    @Query("SELECT * FROM JournalEntryDbEntity WHERE syncStatus != 'synced'")
    suspend fun getPendingEntries(): List<JournalEntryDbEntity>

    @Query("UPDATE JournalEntryDbEntity SET syncStatus = :status WHERE id = :entryId")
    suspend fun updateSyncStatus(entryId: String, status: String)

    @Query("UPDATE JournalEntryDbEntity SET syncStatus = 'pending_delete' WHERE id = :entryId")
    suspend fun markForDeletion(entryId: String)
}
