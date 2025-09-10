package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class ObserveTasksUseCase @Inject constructor(
    private val todoRepository: ITodoRepository
) {

    operator fun invoke(
        search: String?,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoModel>> {
        return todoRepository.getTasks(search, filterCategory, sortItem)
    }

}