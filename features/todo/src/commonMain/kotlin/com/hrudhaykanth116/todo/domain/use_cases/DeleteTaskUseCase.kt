package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.notification.INotificationScheduler
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class DeleteTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
    private val notificationScheduler: INotificationScheduler,
) {

    suspend operator fun invoke(taskIdsToDelete: List<String>? = null): DomainResult<Unit> {
        taskIdsToDelete?.forEach { taskId ->
            notificationScheduler.cancelReminder(taskId)
        }

        if (taskIdsToDelete == null) {
            notificationScheduler.cancelAllReminders()
        }

        return if (taskIdsToDelete == null) {
            todoRepository.deleteAllTasks()
        } else {
            todoRepository.deleteTasks(taskIdsToDelete)
        }
    }
}