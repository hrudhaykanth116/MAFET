package com.hrudhaykanth116.todo.data.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoTaskDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "completed") val completed: Boolean = false,
    @ColumnInfo(name = "category") val category: String = "General",
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "targetTime") val targetTime: Long? = null,
    @ColumnInfo(name = "timeUpdated") val timeUpdated: Long,
    @ColumnInfo(name = "syncStatus") val syncStatus: String = "synced",
)