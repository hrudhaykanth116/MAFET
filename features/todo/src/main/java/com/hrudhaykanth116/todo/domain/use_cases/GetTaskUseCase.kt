package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.mappers.toDomainModel
import com.hrudhaykanth116.todo.domain.model.TodoModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    // TODO: Add filtering, sorting kind of things.
    suspend operator fun invoke(
        taskId: String? = null,
    ): TodoModel? {

        taskId ?: return null

        // TODO: Let's think about this return type
        return todoRepository.getTodoTask(
            taskId
        )?.toDomainModel()

    }

}