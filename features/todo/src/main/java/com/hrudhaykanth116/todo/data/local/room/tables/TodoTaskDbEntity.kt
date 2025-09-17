package com.hrudhaykanth116.todo.data.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// hrudhay_check_list: Create separate data classes for local and remote apis
@Entity
data class TodoTaskDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "completed") val completed: Boolean = true,
    @ColumnInfo(name = "category") val category: String = "General",
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "targetTime") val targetTime: Long? = null, // hrudhay_check_list: Add separate timeCreated
    @ColumnInfo(name = "timeUpdated") val timeUpdated: Long, // hrudhay_check_list: Add separate timeCreated

)