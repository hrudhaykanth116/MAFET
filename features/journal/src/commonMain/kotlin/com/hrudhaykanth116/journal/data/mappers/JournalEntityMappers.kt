package com.hrudhaykanth116.journal.data.mappers

import com.hrudhaykanth116.journal.data.local.room.tables.JournalEntryDbEntity
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.model.SyncStatus

fun JournalEntryDbEntity.toDomain(): JournalEntry = JournalEntry(
    id = id,
    title = title,
    body = body,
    mood = mood,
    tags = if (tags.isBlank()) emptyList() else tags.split(",").map { it.trim() },
    createdAt = createdAt,
    updatedAt = updatedAt,
    syncStatus = SyncStatus.fromKey(syncStatus)
)

fun JournalEntry.toEntity(): JournalEntryDbEntity = JournalEntryDbEntity(
    id = id,
    title = title,
    body = body,
    mood = mood,
    tags = tags.joinToString(","),
    createdAt = createdAt,
    updatedAt = updatedAt,
    syncStatus = syncStatus.key
)
