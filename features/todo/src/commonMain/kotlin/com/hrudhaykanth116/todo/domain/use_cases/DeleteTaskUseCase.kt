package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class DeleteTaskUseCase constructor(
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