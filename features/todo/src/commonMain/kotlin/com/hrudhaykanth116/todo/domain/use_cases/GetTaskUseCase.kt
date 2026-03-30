package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class GetTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
) {

    suspend operator fun invoke(
        taskId: String,
    ): DomainResult<TodoModel> {
        return todoRepository.getTodoTask(taskId)
    }

}