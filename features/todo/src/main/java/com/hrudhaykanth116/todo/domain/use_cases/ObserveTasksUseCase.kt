package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.mappers.toDomainModel
import com.hrudhaykanth116.todo.domain.model.TodoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class ObserveTasksUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    // TODO: Add filtering, sorting kind of things.
    operator fun invoke(
    ): Flow<List<TodoModel>> {

        return todoRepository.getTodoTasksFlow().mapLatest { list: List<TodoTaskDbEntity> ->
            list.map { it.toDomainModel() }
        }
    }

}