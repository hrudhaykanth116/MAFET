package com.hrudhaykanth116.todo.domain.model.create

data class CreateTodoParams(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: Int,
    val targetTime: Long?,
) 