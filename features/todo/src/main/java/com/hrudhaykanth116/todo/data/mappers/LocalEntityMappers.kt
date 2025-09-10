package com.hrudhaykanth116.todo.data.mappers

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.domain.model.TodoModel

fun TodoTaskDbEntity.toDomain(): TodoModel = TodoModel(
    id = id,
    title = title,
    description = description,
    completed = completed,
    category = category,
    priority = priority,
    targetTime = targetTime,
)

fun TodoModel.toLocal(timeUpdated: Long): TodoTaskDbEntity = TodoTaskDbEntity(
    id = id,
    title = title,
    description = description,
    completed = completed,
    category = category,
    priority = priority,
    targetTime = targetTime,
    timeUpdated = timeUpdated,
) 