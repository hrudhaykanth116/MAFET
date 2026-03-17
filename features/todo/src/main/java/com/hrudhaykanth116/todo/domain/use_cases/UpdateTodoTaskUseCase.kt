package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class UpdateTodoTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
) {

    suspend operator fun invoke(todoModel: TodoModel): RepoResultWrapper<Unit> {
        if (todoModel.id.isBlank()) {
            return RepoResultWrapper.Error(ErrorState.Validation)
        }
        if (todoModel.title.isBlank()) {
            return RepoResultWrapper.Error(ErrorState.Validation)
        }
        return todoRepository.updateTodoTask(todoModel)
    }
}
