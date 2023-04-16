package com.hrudhaykanth116.todo.data.remote.models

data class PostTodoRequest(
    val id: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val completed: Boolean? = null,
    val category: String? = null,
)