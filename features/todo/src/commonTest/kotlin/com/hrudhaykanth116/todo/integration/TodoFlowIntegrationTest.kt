package com.hrudhaykanth116.todo.integration

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.domain.use_cases.UpdateTodoTaskUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TodoFlowIntegrationTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var createUseCase: CreateTodoTaskUseCase
    private lateinit var updateUseCase: UpdateTodoTaskUseCase
    private lateinit var deleteUseCase: DeleteTaskUseCase
    private lateinit var getUseCase: GetTaskUseCase
    private lateinit var observeUseCase: ObserveTasksUseCase

    @BeforeTest
    fun setup() {
        repository = FakeTodoRepository()
        createUseCase = CreateTodoTaskUseCase(repository)
        updateUseCase = UpdateTodoTaskUseCase(repository)
        deleteUseCase = DeleteTaskUseCase(repository)
        getUseCase = GetTaskUseCase(repository)
        observeUseCase = ObserveTasksUseCase(repository)
    }

    @Test
    fun completeCreateReadUpdateDeleteFlow() = runTest {
        val task = TodoModel(
            id = "flow-1",
            title = "Integration test task",
            description = "Testing CRUD flow",
            category = TaskCategory.WORK,
            priority = 4
        )

        val createResult = createUseCase(task)
        assertTrue(createResult is DomainResult.Success)

        val getResult = getUseCase("flow-1")
        assertTrue(getResult is DomainResult.Success)
        assertEquals("Integration test task", (getResult as DomainResult.Success).data.title)

        val updatedTask = task.copy(title = "Updated title", priority = 5)
        val updateResult = updateUseCase(updatedTask)
        assertTrue(updateResult is DomainResult.Success)

        val getUpdatedResult = getUseCase("flow-1")
        assertTrue(getUpdatedResult is DomainResult.Success)
        assertEquals("Updated title", (getUpdatedResult as DomainResult.Success).data.title)
        assertEquals(5, getUpdatedResult.data.priority)

        val deleteResult = deleteUseCase(listOf("flow-1"))
        assertTrue(deleteResult is DomainResult.Success)

        val getDeletedResult = getUseCase("flow-1")
        assertTrue(getDeletedResult is DomainResult.Error)
    }

    @Test
    fun createMultipleAndObserveWithFilters() = runTest {
        val tasks = listOf(
            TodoModel(id = "1", title = "Work task 1", category = TaskCategory.WORK, priority = 5),
            TodoModel(id = "2", title = "Work task 2", category = TaskCategory.WORK, priority = 3),
            TodoModel(id = "3", title = "Personal task", category = TaskCategory.PERSONAL, priority = 4),
            TodoModel(id = "4", title = "Shopping list", category = TaskCategory.SHOPPING, priority = 2)
        )

        tasks.forEach { createUseCase(it) }

        val allTasks = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(4, allTasks.size)

        val workTasks = observeUseCase(null, TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, workTasks.size)
        assertTrue(workTasks.all { it.category == TaskCategory.WORK })

        val searchResults = observeUseCase("Work", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, searchResults.size)
    }

    @Test
    fun observeReflectsChangesAfterUpdate() = runTest {
        createUseCase(TodoModel(id = "obs-1", title = "Original", priority = 1))

        val before = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals("Original", before[0].title)

        updateUseCase(TodoModel(id = "obs-1", title = "Modified", priority = 1))

        val after = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals("Modified", after[0].title)
    }

    @Test
    fun observeReflectsChangesAfterDelete() = runTest {
        createUseCase(TodoModel(id = "del-1", title = "Task 1"))
        createUseCase(TodoModel(id = "del-2", title = "Task 2"))

        val before = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, before.size)

        deleteUseCase(listOf("del-1"))

        val after = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(1, after.size)
        assertEquals("del-2", after[0].id)
    }

    @Test
    fun deleteAllClearsRepository() = runTest {
        createUseCase(TodoModel(id = "1", title = "Task 1"))
        createUseCase(TodoModel(id = "2", title = "Task 2"))
        createUseCase(TodoModel(id = "3", title = "Task 3"))

        val before = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(3, before.size)

        deleteUseCase(null)

        val after = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(0, after.size)
    }

    @Test
    fun sortingByPriorityOrdersCorrectly() = runTest {
        createUseCase(TodoModel(id = "1", title = "Low", priority = 1))
        createUseCase(TodoModel(id = "2", title = "High", priority = 5))
        createUseCase(TodoModel(id = "3", title = "Medium", priority = 3))

        val sorted = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals("High", sorted[0].title)
        assertEquals("Medium", sorted[1].title)
        assertEquals("Low", sorted[2].title)
    }

    @Test
    fun combinedSearchAndCategoryFilter() = runTest {
        createUseCase(TodoModel(id = "1", title = "Buy office supplies", category = TaskCategory.WORK))
        createUseCase(TodoModel(id = "2", title = "Buy groceries", category = TaskCategory.SHOPPING))
        createUseCase(TodoModel(id = "3", title = "Work meeting", category = TaskCategory.WORK))
        createUseCase(TodoModel(id = "4", title = "Buy birthday gift", category = TaskCategory.PERSONAL))

        val workBuyTasks = observeUseCase("Buy", TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(1, workBuyTasks.size)
        assertEquals("Buy office supplies", workBuyTasks[0].title)
    }

    @Test
    fun updateNonExistentTaskReturnsError() = runTest {
        val nonExistent = TodoModel(id = "ghost", title = "Does not exist")
        val result = updateUseCase(nonExistent)
        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun createWithDuplicateIdOverwrites() = runTest {
        createUseCase(TodoModel(id = "dup", title = "First version"))
        createUseCase(TodoModel(id = "dup", title = "Second version"))

        val tasks = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, tasks.size)
    }

    @Test
    fun searchIsCaseInsensitive() = runTest {
        createUseCase(TodoModel(id = "1", title = "UPPERCASE TASK"))
        createUseCase(TodoModel(id = "2", title = "lowercase task"))
        createUseCase(TodoModel(id = "3", title = "MixedCase Task"))

        val results = observeUseCase("task", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(3, results.size)
    }

    @Test
    fun searchMatchesDescription() = runTest {
        createUseCase(TodoModel(id = "1", title = "Meeting", description = "Discuss project budget"))
        createUseCase(TodoModel(id = "2", title = "Budget review"))

        val results = observeUseCase("budget", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, results.size)
    }
}
