package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.data.data_source.local.FakeLocal
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class TodoRepositoryTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var localDataSource: FakeLocal
    private lateinit var timeProvider: TimeProvider
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var repository: TodoRepository

    @Before
    fun setup() {
        localDataSource = FakeLocal()
        timeProvider = mock(TimeProvider::class.java)
        networkMonitor = mock(NetworkMonitor::class.java)
        `when`(timeProvider.currentTimeMillis()).thenReturn(1000L)
        `when`(networkMonitor.internetAvailabilityStateFlow).thenReturn(MutableStateFlow(true))
        repository = TodoRepository(localDataSource, timeProvider, networkMonitor, dispatcher)
    }

    @Test
    fun createTodoTask_persistsLocally() = runTest(dispatcher) {
        val todoModel = TodoModel(
            id = "1",
            title = "Test Task",
            description = "Description",
            category = TaskCategory.WORK,
            priority = 1
        )

        val result = repository.createTodoTask(todoModel)

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals(1, localDataSource.tasks.size)
        assertEquals("Test Task", localDataSource.tasks[0].title)
    }

    @Test
    fun getTodoTask_returnsTask_whenExists() = runTest(dispatcher) {
        localDataSource.addTask(
            TodoTaskDbEntity(
                id = "1",
                title = "Existing",
                description = "",
                priority = 2,
                timeUpdated = 500L
            )
        )

        val result = repository.getTodoTask("1")

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals("Existing", (result as RepoResultWrapper.Success).data.title)
    }

    @Test
    fun getTodoTask_returnsError_whenNotFound() = runTest(dispatcher) {
        val result = repository.getTodoTask("nonexistent")

        assertTrue(result is RepoResultWrapper.Error)
    }

    @Test
    fun deleteTasks_removesSpecifiedTasks() = runTest(dispatcher) {
        localDataSource.addTask(
            TodoTaskDbEntity(id = "1", title = "Task1", priority = 1, timeUpdated = 100L)
        )
        localDataSource.addTask(
            TodoTaskDbEntity(id = "2", title = "Task2", priority = 2, timeUpdated = 200L)
        )

        val result = repository.deleteTasks(listOf("1"))

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals(1, localDataSource.tasks.size)
        assertEquals("2", localDataSource.tasks[0].id)
    }

    @Test
    fun observeTasks_emitsFilteredResults() = runTest(dispatcher) {
        localDataSource.addTask(
            TodoTaskDbEntity(id = "1", title = "Work task", category = TaskCategory.WORK.key, priority = 1, timeUpdated = 100L)
        )
        localDataSource.addTask(
            TodoTaskDbEntity(id = "2", title = "Personal task", category = TaskCategory.PERSONAL.key, priority = 2, timeUpdated = 200L)
        )

        val results = repository.observeTasks(null, TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(1, results.size)
        assertEquals("Work task", results[0].title)
    }

    @Test
    fun updateTodoTask_updatesExistingTask() = runTest(dispatcher) {
        localDataSource.addTask(
            TodoTaskDbEntity(id = "1", title = "Original", priority = 1, timeUpdated = 100L)
        )

        val updated = TodoModel(id = "1", title = "Updated", priority = 2)
        val result = repository.updateTodoTask(updated)

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals("Updated", localDataSource.tasks.find { it.id == "1" }?.title)
    }

    @Test
    fun updateTodoTask_returnsError_whenTaskNotFound() = runTest(dispatcher) {
        val nonExistent = TodoModel(id = "999", title = "Does not exist", priority = 1)
        val result = repository.updateTodoTask(nonExistent)

        assertTrue(result is RepoResultWrapper.Error)
    }

    @Test
    fun createTodoTask_marksPendingCreate_whenOffline() = runTest(dispatcher) {
        `when`(networkMonitor.internetAvailabilityStateFlow).thenReturn(MutableStateFlow(false))

        val todoModel = TodoModel(id = "1", title = "Offline Task", priority = 1)
        repository.createTodoTask(todoModel)

        assertEquals("pending_create", localDataSource.tasks[0].syncStatus)
    }

    @Test
    fun createTodoTask_marksSynced_whenOnline() = runTest(dispatcher) {
        val todoModel = TodoModel(id = "1", title = "Online Task", priority = 1)
        repository.createTodoTask(todoModel)

        assertEquals("synced", localDataSource.tasks[0].syncStatus)
    }

    @Test
    fun deleteTasks_marksForDeletion_whenOffline() = runTest(dispatcher) {
        `when`(networkMonitor.internetAvailabilityStateFlow).thenReturn(MutableStateFlow(false))
        localDataSource.addTask(
            TodoTaskDbEntity(id = "1", title = "Task", priority = 1, timeUpdated = 100L)
        )

        repository.deleteTasks(listOf("1"))

        assertEquals(1, localDataSource.tasks.size)
        assertEquals("pending_delete", localDataSource.tasks[0].syncStatus)
    }

    @Test
    fun observeTasks_excludesPendingDeleteTasks() = runTest(dispatcher) {
        localDataSource.addTask(
            TodoTaskDbEntity(id = "1", title = "Active", priority = 1, timeUpdated = 100L, syncStatus = "synced")
        )
        localDataSource.addTask(
            TodoTaskDbEntity(id = "2", title = "Deleted", priority = 2, timeUpdated = 200L, syncStatus = "pending_delete")
        )

        val results = repository.observeTasks(null, null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(1, results.size)
        assertEquals("Active", results[0].title)
    }
}