package com.hrudhaykanth116.mafet.todo.domain.use_cases

import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.mafet.todo.data.repositories.TodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTodoTasksUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    suspend operator fun invoke(): List<TodoTaskDbEntity>{
        val todoTasksListDbEntity: List<TodoTaskDbEntity> = todoRepository.getTodoTask()
        return todoTasksListDbEntity
    }

}