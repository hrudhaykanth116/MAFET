package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow

class ObserveTasksUseCase constructor(
    private val todoRepository: ITodoRepository
) {

    operator fun invoke(
        search: String?,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoModel>> {
        return todoRepository.observeTasks(search, filterCategory, sortItem)
    }
}