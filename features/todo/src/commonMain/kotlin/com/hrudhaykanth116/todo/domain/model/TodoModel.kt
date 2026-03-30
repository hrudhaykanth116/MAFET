package com.hrudhaykanth116.todo.domain.model

data class TodoModel(
    val id: String,
    val title: String,
    val description: String = "",
    val completed: Boolean = false,
    val category: TaskCategory = TaskCategory.DEFAULT,
    val priority: Int = TodoDefaults.PRIORITY,
    val targetTime: Long? = null,
    val syncStatus: SyncStatus = SyncStatus.DEFAULT,
)
