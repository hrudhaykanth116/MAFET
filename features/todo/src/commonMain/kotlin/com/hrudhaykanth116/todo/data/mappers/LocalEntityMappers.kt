package com.hrudhaykanth116.todo.data.mappers

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.domain.model.SyncStatus
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel

fun TodoTaskDbEntity.toDomain(): TodoModel = TodoModel(
    id = id,
    title = title,
    description = description,
    completed = completed,
    category = TaskCategory.fromKey(category),
    priority = priority,
    targetTime = targetTime,
    syncStatus = SyncStatus.fromKey(syncStatus),
)

fun TodoModel.toLocal(timeUpdated: Long): TodoTaskDbEntity = TodoTaskDbEntity(
    id = id,
    title = title,
    description = description,
    completed = completed,
    category = category.key,
    priority = priority,
    targetTime = targetTime,
    timeUpdated = timeUpdated,
    syncStatus = syncStatus.key,
) 