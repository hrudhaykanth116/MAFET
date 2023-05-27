package com.hrudhaykanth116.todo.data.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: Create separate data classes for local and remote apis
@Entity
data class TodoTaskDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "completed") val completed: Boolean = true,
    @ColumnInfo(name = "category") val category: String = "General",
)