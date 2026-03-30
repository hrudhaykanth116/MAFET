package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class GetAllTasksUseCase(
    private val todoRepository: ITodoRepository
) {

    suspend operator fun invoke(): List<TodoModel> {
        return todoRepository.getTasks()
    }
}
