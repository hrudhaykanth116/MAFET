package com.hrudhaykanth116.mafet.todo.utils

import com.hrudhaykanth116.mafet.todo.models.ToDoTask
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

internal class StatisticsHelperTest{

    // subjectUnderTest_actionOrInput_resultState

    @Test
    fun getActiveAndCompletedStats_zeroCompleted_percentCheck(){

        // Arrange or Given
        val todoTasksList: List<ToDoTask> = listOf(
            ToDoTask("Test", true),
            ToDoTask("Test", true),
            ToDoTask("Test", true)
        )

        // Act or When
        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksList)

        // Assert or then
        assertEquals(0f, activeAndCompletedStats.completePercentage)
        assertEquals(100f, activeAndCompletedStats.activePercentage)

        assertThat(activeAndCompletedStats.completePercentage, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_twoCompletedThreeActive_percentCheck(){

        val todoTasksList: List<ToDoTask> = listOf(
            ToDoTask("Test1", true),
            ToDoTask("Test2", true),
            ToDoTask("Test3", true),
            ToDoTask("Test4", false),
            ToDoTask("Test5", false)
        )

        val activeAndCompletedStats = getActiveAndCompletedStats(todoTasksList)
        assertEquals(40f, activeAndCompletedStats.completePercentage)
        assertEquals(60f, activeAndCompletedStats.activePercentage)

    }

    @Test
    fun getActiveAndCompletedStats_zeroTasks_percentCheck(){

        val todoTasksList: List<ToDoTask> = emptyList()

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