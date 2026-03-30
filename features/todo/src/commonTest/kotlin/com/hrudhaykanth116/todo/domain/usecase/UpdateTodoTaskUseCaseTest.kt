package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.UpdateTodoTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UpdateTodoTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: UpdateTodoTaskUseCase

    private fun setup() {
        repository = FakeTodoRepository()
        useCase = UpdateTodoTaskUseCase(repository)
    }

    @Test
    fun updateExistingTask_succeeds() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Original"))

        val updated = TodoModel(id = "1", title = "Updated", category = TaskCategory.WORK)
        val result = useCase(updated)

        assertTrue(result is DomainResult.Success)
        assertEquals("Updated", repository.getTasks()[0].title)
        assertEquals(TaskCategory.WORK, repository.getTasks()[0].category)
    }

    @Test
    fun updateNonExistentTask_returnsError() = runTest {
        setup()
        val task = TodoModel(id = "999", title = "Does not exist")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun updateWithBlankTitle_returnsValidationError() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Original"))

        val task = TodoModel(id = "1", title = "   ")
        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
        assertEquals("Original", repository.getTasks()[0].title)
    }

    @Test
    fun updateWithEmptyId_returnsValidationError() = runTest {
        setup()
        val task = TodoModel(id = "", title = "Valid Title")

        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }

    @Test
    fun update_returnsErrorWhenRepositoryFails() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Original"))
        repository.shouldReturnError = true

        val task = TodoModel(id = "1", title = "Updated")
        val result = useCase(task)

        assertTrue(result is DomainResult.Error)
    }
}
