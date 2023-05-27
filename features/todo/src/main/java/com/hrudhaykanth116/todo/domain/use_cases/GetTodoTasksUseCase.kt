package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.mappers.toDomainModel
import com.hrudhaykanth116.todo.domain.model.TodoModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTodoTasksUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    // TODO: Add filtering, sorting kind of things.
    suspend operator fun invoke(
        taskId: String? = null,
    ): List<TodoModel>? {

        // TODO: Let's think about this return type
        return if (taskId != null) {

            val domainModel = todoRepository.getTodoTask(
                taskId
            )?.toDomainModel() ?: return null

            listOf(domainModel)
        } else {
            todoRepository.getTodoTask().map {
                it.toDomainModel()
            }
        }
    }

}