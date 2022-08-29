package com.hrudhaykanth116.mafet.todo.utils

import com.hrudhaykanth116.mafet.todo.ui.data_models.ToDoTaskUIState
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

internal class StatisticsHelperTest{

    // subjectUnderTest_actionOrInput_resultState

    @Test
    fun getActiveAndCompletedStats_zeroCompleted_percentCheck(){

        // Arrange or Given
        val todoTasksLists: List<ToDoTaskUIState> = listOf(
            ToDoTaskUIState("Test", true),
            ToDoTaskUIState("Test", true),
            ToDoTaskUIState("Test", true)
        )

        // Act or When
        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksLists)

        // Assert or then
        assertEquals(0f, activeAndCompletedStats.completePercentage)
        assertEquals(100f, activeAndCompletedStats.activePercentage)

        assertThat(activeAndCompletedStats.completePercentage, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_twoCompletedThreeActive_percentCheck(){

        val todoTasksLists: List<ToDoTaskUIState> = listOf(
            ToDoTaskUIState("Test1", true),
            ToDoTaskUIState("Test2", true),
            ToDoTaskUIState("Test3", true),
            ToDoTaskUIState("Test4", false),
            ToDoTaskUIState("Test5", false)
        )

        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksLists)
        assertEquals(40f, activeAndCompletedStats.completePercentage)
        assertEquals(60f, activeAndCompletedStats.activePercentage)

    }

    @Test
    fun getActiveAndCompletedStats_zeroTasks_percentCheck(){

        val todoTasksList: List<ToDoTaskUIState> = emptyList()

        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksList)
        assertEquals(0f, activeAndCompletedStats.completePercentage)
        assertEquals(0f, activeAndCompletedStats.activePercentage)

    }

    @Test
    fun getActiveAndCompletedStats_nullTasks_percentCheck(){

        val todoTasksList = null

        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksList)
        assertEquals(0f, activeAndCompletedStats.completePercentage)
        assertEquals(0f, activeAndCompletedStats.activePercentage)

    }



}