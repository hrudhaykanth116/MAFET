package com.hrudhaykanth116.journal.domain.model

data class JournalEntry(
    val id: String,
    val title: String,
    val body: String,
    val mood: Int = 3,
    val tags: List<String> = emptyList(),
    val createdAt: Long,
    val updatedAt: Long,
    val syncStatus: SyncStatus = SyncStatus.DEFAULT
)
