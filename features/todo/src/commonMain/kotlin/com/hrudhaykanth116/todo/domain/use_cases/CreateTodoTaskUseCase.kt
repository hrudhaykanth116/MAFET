package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class CreateTodoTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
) {

    suspend operator fun invoke(todoModel: TodoModel): DomainResult<Unit> {
        if (todoModel.id.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Task ID is required"))
        }
        if (todoModel.title.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Task title is required"))
        }
        return todoRepository.createTodoTask(todoModel)
    }
}