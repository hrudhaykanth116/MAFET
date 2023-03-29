package com.hrudhaykanth116.mafet.todo.utils

import com.hrudhaykanth116.mafet.todo.ui.models.ToDoTaskUIState

fun getActiveAndCompletedStats(toDoTaskUIStates: List<ToDoTaskUIState>?): StatsResult {

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