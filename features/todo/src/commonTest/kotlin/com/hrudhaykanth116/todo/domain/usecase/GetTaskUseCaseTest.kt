package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: GetTaskUseCase

    private fun setup() {
        repository = FakeTodoRepository()
        useCase = GetTaskUseCase(repository)
    }

    @Test
    fun getTask_returnsTaskWhenItExists() = runTest {
        setup()
        val task = TodoModel(id = "123", title = "My Task", description = "Details", priority = 2)
        repository.addTask(task)

        val result = useCase("123")

        assertTrue(result is DomainResult.Success)
        val returnedTask = (result as DomainResult.Success).data
        assertEquals("My Task", returnedTask.title)
        assertEquals("Details", returnedTask.description)
        assertEquals(2, returnedTask.priority)
    }

    @Test
    fun getTask_returnsNotFoundWhenTaskDoesNotExist() = runTest {
        setup()
        val result = useCase("nonexistent")

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun getTask_returnsErrorWhenRepositoryFails() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Task"))
        repository.shouldReturnError = true

        val result = useCase("1")

        assertTrue(result is DomainResult.Error)
    }
}
