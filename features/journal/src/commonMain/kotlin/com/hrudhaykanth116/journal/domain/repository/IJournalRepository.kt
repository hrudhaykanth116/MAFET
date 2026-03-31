package com.hrudhaykanth116.journal.domain.repository

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface IJournalRepository {

    fun observeEntries(search: String?): Flow<List<JournalEntry>>

    suspend fun getEntry(id: String): DomainResult<JournalEntry>

    suspend fun createEntry(entry: JournalEntry): DomainResult<Unit>

    suspend fun updateEntry(entry: JournalEntry): DomainResult<Unit>

    suspend fun deleteEntry(id: String): DomainResult<Unit>

    suspend fun deleteAllEntries(): DomainResult<Unit>
}
