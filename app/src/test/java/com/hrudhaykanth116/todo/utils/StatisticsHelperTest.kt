// package com.hrudhaykanth116.todo.utils
//
// import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
// import org.hamcrest.CoreMatchers.`is`
// import org.junit.Assert.assertEquals
// import org.junit.Assert.assertThat
// import org.junit.Test
//
// internal class StatisticsHelperTest{
//
//     // subjectUnderTest_actionOrInput_resultState
//
//     @Test
//     fun getActiveAndCompletedStats_zeroCompleted_percentCheck(){
//
//         // Arrange or Given
//         val todoTasksLists: List<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState> = listOf(
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test", true),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test", true),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test", true)
//         )
//
//         // Act or When
//         val activeAndCompletedStats =
//             com.hrudhaykanth116.todo.utils.getActiveAndCompletedStats(todoTasksLists)
//
//         // Assert or then
//         assertEquals(0f, activeAndCompletedStats.completePercentage)
//         assertEquals(100f, activeAndCompletedStats.activePercentage)
//
//         assertThat(activeAndCompletedStats.completePercentage, `is`(0f))
//
//     }
//
//     @Test
//     fun getActiveAndCompletedStats_twoCompletedThreeActive_percentCheck(){
//
//         val todoTasksLists: List<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState> = listOf(
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test1", true),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test2", true),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test3", true),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test4", false),
//             com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState("Test5", false)
//         )
//
//         val activeAndCompletedStats =
//             com.hrudhaykanth116.todo.utils.getActiveAndCompletedStats(todoTasksLists)
//         assertEquals(40f, activeAndCompletedStats.completePercentage)
//         assertEquals(60f, activeAndCompletedStats.activePercentage)
//
//     }
//
//     @Test
//     fun getActiveAndCompletedStats_zeroTasks_percentCheck(){
//
//         val todoTasksList: List<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState> = emptyList()
//
//         val activeAndCompletedStats =
//             com.hrudhaykanth116.todo.utils.getActiveAndCompletedStats(todoTasksList)
//         assertEquals(0f, activeAndCompletedStats.completePercentage)
//         assertEquals(0f, activeAndCompletedStats.activePercentage)
//
//     }
//
//     @Test
//     fun getActiveAndCompletedStats_nullTasks_percentCheck(){
//
//         val todoTasksList = null
//
//         val activeAndCompletedStats =
//             com.hrudhaykanth116.todo.utils.getActiveAndCompletedStats(todoTasksList)
//         assertEquals(0f, activeAndCompletedStats.completePercentage)
//         assertEquals(0f, activeAndCompletedStats.activePercentage)
//
//     }
//
//
//
// }