package com.hrudhaykanth116.todo.domain.model

data class TodoModel(
    val id: String,
    val title: String,
    val description: String = "",
    val completed: Boolean = true,
    val category: String = "General",
)
