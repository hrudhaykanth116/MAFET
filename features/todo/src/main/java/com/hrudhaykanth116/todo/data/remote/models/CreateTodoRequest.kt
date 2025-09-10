package com.hrudhaykanth116.todo.data.remote.models

data class CreateTodoRequest(
    val id: Long,
    val title: String,
    val description: String? = null,
    val category: String,
    val active: Boolean,
)