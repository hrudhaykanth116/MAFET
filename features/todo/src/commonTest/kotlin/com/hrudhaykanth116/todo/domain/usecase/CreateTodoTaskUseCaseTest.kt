package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.testutils.FakeNotificationScheduler
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CreateTodoTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: CreateTodoTaskUseCase

    private fun setup() {
        repository = FakeTodoRepository()
        useCase = CreateTodoTaskUseCase(repository, FakeNotificationScheduler())
    }

    @Test
    fun createTask_savesTaskToRepository() = runTest {
        setup()
        val task = TodoModel(
            id = "1",
            title = "New Task",
            description = "Task description",
            category = TaskCategory.WORK,
            priority = 1
        )

        val result = useCase(task)

        assertTrue(result is DomainResult.Success)
        assertEquals(1, repository.getAllTasks().size)
        assertEquals("New Task", repository.getAllTasks()[0].title)
    }

    @Test
    fun createTask_returnsErrorWhenRepositoryFails() = runTest {
        setup()
        repository.shouldReturnError = true
        val task = TodoModel(id = "1", title = "Task")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun createTask_handlesMultipleTasks() = runTest {
        setup()
        useCase(TodoModel(id = "1", title = "First"))
        useCase(TodoModel(id = "2", title = "Second"))
        useCase(TodoModel(id = "3", title = "Third"))

        assertEquals(3, repository.getAllTasks().size)
    }

    @Test
    fun createdTask_hasCorrectDefaultValues() = runTest {
        setup()
        val task = TodoModel(id = "1", title = "Minimal Task")

        useCase(task)

        val saved = repository.getAllTasks()[0]
        assertEquals(false, saved.completed)
        assertEquals(TaskCategory.DEFAULT, saved.category)
        assertEquals(3, saved.priority)
    }

    @Test
    fun createTask_withBlankTitle_returnsValidationError() = runTest {
        setup()
        val task = TodoModel(id = "1", title = "   ")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
        assertEquals(0, repository.getAllTasks().size)
    }

    @Test
    fun createTask_withEmptyId_returnsValidationError() = runTest {
        setup()
        val task = TodoModel(id = "", title = "Valid Title")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
        assertEquals(0, repository.getAllTasks().size)
    }
}
