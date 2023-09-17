package com.hrudhaykanth116.todo.domain.model

data class TodoModel(
    val id: String,
    val title: String,
    val description: String = "",
    val completed: Boolean = true,
    val category: String = "General",
    val priority: Int = 3,
    val targetTime: Long? = null,
)
