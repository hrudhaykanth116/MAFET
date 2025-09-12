package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTaskUseCase @Inject constructor(
    private val todoRepository: ITodoRepository,
) {

    // TODO: Add filtering, sorting kind of things.
    suspend operator fun invoke(
        taskId: String,
    ): RepoResultWrapper<TodoModel> {
        return todoRepository.getTodoTask(taskId)
    }

}