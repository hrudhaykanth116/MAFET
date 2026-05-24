// package com.hrudhaykanth116.todo.data.repositories
//
// import com.hrudhaykanth116.core.common.time.TimeProvider
// import com.hrudhaykanth116.core.domain.result.DomainResult
// import com.hrudhaykanth116.core.ui.NetworkMonitor
// import com.hrudhaykanth116.todo.data.data_source.local.FakeLocal
// import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
// import com.hrudhaykanth116.todo.domain.model.TaskCategory
// import com.hrudhaykanth116.todo.domain.model.TodoModel
// import com.hrudhaykanth116.todo.domain.model.TodoDefaults
// import io.mockk.every
// import io.mockk.mockk
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.first
// import kotlinx.coroutines.test.StandardTestDispatcher
// import kotlinx.coroutines.test.runTest
// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertTrue
//
// @OptIn(ExperimentalCoroutinesApi::class)
// class TodoRepositoryTest {
//
//     private val dispatcher = StandardTestDispatcher()
//     private lateinit var localDataSource: FakeLocal
//     private lateinit var timeProvider: TimeProvider
//     private lateinit var networkMonitor: NetworkMonitor
//     private lateinit var repository: TodoRepository
//
//     private fun setup(isOnline: Boolean = true) {
//         localDataSource = FakeLocal()
//         timeProvider = mockk()
//         networkMonitor = mockk()
//         every { timeProvider.currentTimeMillis() } returns 1000L
//         every { networkMonitor.internetAvailabilityStateFlow } returns MutableStateFlow(isOnline)
//         repository = TodoRepository(localDataSource, timeProvider, networkMonitor, dispatcher)
//     }
//
//     @Test
//     fun createTodoTask_persistsLocally() = runTest(dispatcher) {
//         setup()
//         val todoModel = TodoModel(
//             id = "1",
//             title = "Test Task",
//             description = "Description",
//             category = TaskCategory.WORK,
//             priority = 1
//         )
//
//         val result = repository.createTodoTask(todoModel)
//
//         assertTrue(result is DomainResult.Success)
//         assertEquals(1, localDataSource.tasks.size)
//         assertEquals("Test Task", localDataSource.tasks[0].title)
//     }
//
//     @Test
//     fun getTodoTask_returnsTask_whenExists() = runTest(dispatcher) {
//         setup()
//         localDataSource.addTask(
//             TodoTaskDbEntity(
//                 id = "1",
//                 title = "Existing",
//                 description = "",
//                 priority = 2,
//                 timeUpdated = 500L
//             )
//         )
//
//         val result = repository.getTodoTask("1")
//
//         assertTrue(result is DomainResult.Success)
//         assertEquals("Existing", (result as DomainResult.Success).data.title)
//     }
//
//     @Test
//     fun getTodoTask_returnsError_whenNotFound() = runTest(dispatcher) {
//         setup()
//         val result = repository.getTodoTask("nonexistent")
//
//         assertTrue(result is DomainResult.Error)
//     }
//
//     @Test
//     fun deleteTasks_removesSpecifiedTasks() = runTest(dispatcher) {
//         setup()
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "1", title = "Task1", priority = 1, timeUpdated = 100L)
//         )
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "2", title = "Task2", priority = 2, timeUpdated = 200L)
//         )
//
//         val result = repository.deleteTasks(listOf("1"))
//
//         assertTrue(result is DomainResult.Success)
//         assertEquals(1, localDataSource.tasks.size)
//         assertEquals("2", localDataSource.tasks[0].id)
//     }
//
//     @Test
//     fun observeTasks_emitsFilteredResults() = runTest(dispatcher) {
//         setup()
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "1", title = "Work task", category = TaskCategory.WORK.key, priority = 1, timeUpdated = 100L)
//         )
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "2", title = "Personal task", category = TaskCategory.PERSONAL.key, priority = 2, timeUpdated = 200L)
//         )
//
//         val results = repository.observeTasks(null, TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()
//
//         assertEquals(1, results.size)
//         assertEquals("Work task", results[0].title)
//     }
//
//     @Test
//     fun updateTodoTask_updatesExistingTask() = runTest(dispatcher) {
//         setup()
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "1", title = "Original", priority = 1, timeUpdated = 100L)
//         )
//
//         val updated = TodoModel(id = "1", title = "Updated", priority = 2)
//         val result = repository.updateTodoTask(updated)
//
//         assertTrue(result is DomainResult.Success)
//         assertEquals("Updated", localDataSource.tasks.find { it.id == "1" }?.title)
//     }
//
//     @Test
//     fun updateTodoTask_returnsError_whenTaskNotFound() = runTest(dispatcher) {
//         setup()
//         val nonExistent = TodoModel(id = "999", title = "Does not exist", priority = 1)
//         val result = repository.updateTodoTask(nonExistent)
//
//         assertTrue(result is DomainResult.Error)
//     }
//
//     @Test
//     fun createTodoTask_marksPendingCreate_whenOffline() = runTest(dispatcher) {
//         setup(isOnline = false)
//
//         val todoModel = TodoModel(id = "1", title = "Offline Task", priority = 1)
//         repository.createTodoTask(todoModel)
//
//         assertEquals("pending_create", localDataSource.tasks[0].syncStatus)
//     }
//
//     @Test
//     fun createTodoTask_marksSynced_whenOnline() = runTest(dispatcher) {
//         setup(isOnline = true)
//         val todoModel = TodoModel(id = "1", title = "Online Task", priority = 1)
//         repository.createTodoTask(todoModel)
//
//         assertEquals("synced", localDataSource.tasks[0].syncStatus)
//     }
//
//     @Test
//     fun deleteTasks_marksForDeletion_whenOffline() = runTest(dispatcher) {
//         setup(isOnline = false)
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "1", title = "Task", priority = 1, timeUpdated = 100L)
//         )
//
//         repository.deleteTasks(listOf("1"))
//
//         assertEquals(1, localDataSource.tasks.size)
//         assertEquals("pending_delete", localDataSource.tasks[0].syncStatus)
//     }
//
//     @Test
//     fun observeTasks_excludesPendingDeleteTasks() = runTest(dispatcher) {
//         setup()
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "1", title = "Active", priority = 1, timeUpdated = 100L, syncStatus = "synced")
//         )
//         localDataSource.addTask(
//             TodoTaskDbEntity(id = "2", title = "Deleted", priority = 2, timeUpdated = 200L, syncStatus = "pending_delete")
//         )
//
//         val results = repository.observeTasks(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
//
//         assertEquals(1, results.size)
//         assertEquals("Active", results[0].title)
//     }
// }
