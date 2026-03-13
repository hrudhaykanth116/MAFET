package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteTaskUseCase @Inject constructor(
    private val todoRepository: ITodoRepository,
) {

    suspend operator fun invoke(taskIdsToDelete: List<String>? = null): RepoResultWrapper<Unit> {
        return if (taskIdsToDelete == null) {
            todoRepository.deleteAllTasks()
        } else {
            todoRepository.deleteTasks(taskIdsToDelete)
        }
    }
}