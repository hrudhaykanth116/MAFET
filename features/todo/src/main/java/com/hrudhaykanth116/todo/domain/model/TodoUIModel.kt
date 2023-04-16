package com.hrudhaykanth116.todo.domain.model

data class TodoUIModel(
    val id: Long,
    val title: String,
    val description: String? = null,
    val completed: Boolean = true,
    val category: TaskCategory = TaskCategory.GENERAL,
)