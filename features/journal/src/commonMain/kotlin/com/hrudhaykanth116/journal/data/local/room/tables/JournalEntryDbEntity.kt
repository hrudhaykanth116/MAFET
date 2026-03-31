package com.hrudhaykanth116.journal.data.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JournalEntryDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "mood") val mood: Int,
    @ColumnInfo(name = "tags") val tags: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
    @ColumnInfo(name = "updatedAt") val updatedAt: Long,
    @ColumnInfo(name = "syncStatus") val syncStatus: String = "synced"
)
