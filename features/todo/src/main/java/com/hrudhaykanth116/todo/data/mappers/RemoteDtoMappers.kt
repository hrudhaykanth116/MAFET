package com.hrudhaykanth116.todo.data.mappers

import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.domain.model.TodoModel

fun GetTodoResponse.TodoData.toDomain(): TodoModel? {
    val idStr = this.id?.toString() ?: return null
    return TodoModel(
        id = idStr,
        title = this.title ?: "",
        description = this.description ?: "",
        completed = this.completed ?: false,
        category = this.category ?: "General",
        priority = 3,
        targetTime = null,
    )
}

fun PostTodoResponse.TodoData.toDomain(): TodoModel? {
    val idStr = this.id?.toString() ?: return null
    return TodoModel(
        id = idStr,
        title = this.title ?: "",
        description = this.description ?: "",
        completed = this.completed ?: false,
        category = this.category ?: "General",
        priority = 3,
        targetTime = null,
    )
} 