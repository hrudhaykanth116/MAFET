package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        taskId: String,
    ): DataResult<Unit> {

        todoRepository.deleteTask(
            taskId = taskId,
        )

        return DataResult.Success(Unit)

    }
}