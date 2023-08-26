package com.hrudhaykanth116.todo.domain.mappers

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.domain.model.TodoModel

fun TodoTaskDbEntity.toDomainModel(): TodoModel{

    // this ?: return null

    return TodoModel(
        id = id,
        title = title,
        description = description,
        completed = completed,
        category = category

    )

}