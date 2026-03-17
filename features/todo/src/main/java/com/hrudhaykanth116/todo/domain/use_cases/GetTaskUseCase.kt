package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class GetTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
) {

    // hrudhay_check_list: Add filtering, sorting kind of things.
    suspend operator fun invoke(
        taskId: String,
    ): RepoResultWrapper<TodoModel> {
        return todoRepository.getTodoTask(taskId)
    }

}