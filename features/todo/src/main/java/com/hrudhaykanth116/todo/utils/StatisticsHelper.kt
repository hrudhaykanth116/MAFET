package com.hrudhaykanth116.todo.utils

import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState

fun getActiveAndCompletedStats(toDoTaskUIStates: List<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState>?): StatsResult {

    if (toDoTaskUIStates.isNullOrEmpty()) {
        return StatsResult(0f, 0f)
    } else {
        val totalTasksCount = toDoTaskUIStates.size
        val activeTasksCount = toDoTaskUIStates.count { it.data.completed }
        val completedTasksCount = totalTasksCount - activeTasksCount

        return StatsResult(
            (100 * activeTasksCount / totalTasksCount).toFloat(),
            (100 * completedTasksCount / totalTasksCount).toFloat()
        )
    }


}

data class StatsResult(val activePercentage: Float, val completePercentage: Float)