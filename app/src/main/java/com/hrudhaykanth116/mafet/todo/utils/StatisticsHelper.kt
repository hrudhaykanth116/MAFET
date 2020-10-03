package com.hrudhaykanth116.mafet.todo.utils

import com.hrudhaykanth116.mafet.todo.models.ToDoTask

fun getActiveAndCompletedStats(toDoTasks: List<ToDoTask>?): StatsResult {

    if (toDoTasks.isNullOrEmpty()) {
        return StatsResult(0f, 0f)
    } else {
        val totalTasksCount = toDoTasks.size
        val activeTasksCount = toDoTasks.count { it.isActive }
        val completedTasksCount = totalTasksCount - activeTasksCount

        return StatsResult(
            (100 * activeTasksCount / totalTasksCount).toFloat(),
            (100 * completedTasksCount / totalTasksCount).toFloat()
        )
    }


}

data class StatsResult(val activePercentage: Float, val completePercentage: Float)