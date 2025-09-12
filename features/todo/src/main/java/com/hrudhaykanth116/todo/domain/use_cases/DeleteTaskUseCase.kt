package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    /**
     * If null is passed, all are deleted.
     */
    suspend operator fun invoke(
        taskIdsToDelete: List<String>? = null,
    ): RepoResultWrapper<Unit> {

        if (taskIdsToDelete == null) {
            // TODO: A simple check to delete all tasks.
            todoRepository.deleteAllTasks()
        } else {
            todoRepository.deleteTasks(
                taskId = taskIdsToDelete,
            )
        }


        return RepoResultWrapper.Success(Unit)

    }
}