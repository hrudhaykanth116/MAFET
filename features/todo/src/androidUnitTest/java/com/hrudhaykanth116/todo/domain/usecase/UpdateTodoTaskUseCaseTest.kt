package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.UpdateTodoTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateTodoTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: UpdateTodoTaskUseCase

    @Before
    fun setup() {
        repository = FakeTodoRepository()
        useCase = UpdateTodoTaskUseCase(repository)
    }

    @Test
    fun `update existing task succeeds`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Original"))

        val updated = TodoModel(id = "1", title = "Updated", category = TaskCategory.WORK)
        val result = useCase(updated)

        assertTrue(result is DomainResult.Success)
        assertEquals("Updated", repository.getTasks()[0].title)
        assertEquals(TaskCategory.WORK, repository.getTasks()[0].category)
    }

    @Test
    fun `update non-existent task returns error`() = runTest {
        val task = TodoModel(id = "999", title = "Does not exist")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun `update with blank title returns validation error`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Original"))

        val task = TodoModel(id = "1", title = "   ")
        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
        assertEquals("Original", repository.getTasks()[0].title)
    }

    @Test
    fun `update with empty id returns validation error`() = runTest {
        val task = TodoModel(id = "", title = "Valid Title")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun `update returns error when repository fails`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Original"))
        repository.shouldReturnError = true

        val task = TodoModel(id = "1", title = "Updated")
        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }
}
