package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.notification.INotificationScheduler
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository

class CreateTodoTaskUseCase constructor(
    private val todoRepository: ITodoRepository,
    private val notificationScheduler: INotificationScheduler,
) {

    suspend operator fun invoke(todoModel: TodoModel): DomainResult<Unit> {
        if (todoModel.id.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Task ID is required"))
        }
        if (todoModel.title.isBlank()) {
            return DomainResult.Error(DomainError.Validation("Task title is required"))
        }

        val result = todoRepository.createTodoTask(todoModel)

        if (result is DomainResult.Success && todoModel.targetTime != null) {
            notificationScheduler.scheduleReminder(
                taskId = todoModel.id,
                title = todoModel.title,
                triggerTimeMillis = todoModel.targetTime
            )
        }

        return result
    }
}