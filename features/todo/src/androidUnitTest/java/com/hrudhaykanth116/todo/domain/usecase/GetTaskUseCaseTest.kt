package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: GetTaskUseCase

    @Before
    fun setup() {
        repository = FakeTodoRepository()
        useCase = GetTaskUseCase(repository)
    }

    @Test
    fun `get task returns task when it exists`() = runTest {
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
    fun `get task returns not found when task does not exist`() = runTest {
        val result = useCase("nonexistent")

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun `get task returns error when repository fails`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Task"))
        repository.shouldReturnError = true

        val result = useCase("1")

        assertTrue(result is DomainResult.Error)
    }
}
