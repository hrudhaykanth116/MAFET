package com.hrudhaykanth116.composeapp.home.dashboard.domain

import com.hrudhaykanth116.composeapp.home.dashboard.models.TodoSummary
import com.hrudhaykanth116.todo.domain.use_cases.GetAllTasksUseCase

class GetDashboardTodoUseCase(
    private val getAllTasksUseCase: GetAllTasksUseCase
) {

    suspend operator fun invoke(): TodoSummary? {
        val allTasks = getAllTasksUseCase()

        val topPriorityTask = allTasks
            .filter { !it.completed }
            .maxByOrNull { it.priority }
            ?: return null

        return TodoSummary(
            id = topPriorityTask.id,
            title = topPriorityTask.title,
            priority = topPriorityTask.priority
        )
    }
}
