package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTodoTasksUseCase @Inject constructor(
    private val todoRepository: com.hrudhaykanth116.todo.data.repositories.TodoRepository
) {

    suspend operator fun invoke(): List<com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity>{
        val todoTasksListDbEntity: List<com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity> = todoRepository.getTodoTask()
        return todoTasksListDbEntity
    }

}