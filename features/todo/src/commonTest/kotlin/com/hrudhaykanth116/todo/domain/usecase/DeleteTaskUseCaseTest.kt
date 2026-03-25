package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeleteTaskUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: DeleteTaskUseCase

    private fun setup() {
        repository = FakeTodoRepository()
        useCase = DeleteTaskUseCase(repository)
    }

    @Test
    fun deleteSpecificTasks_removesOnlyThoseTasks() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Task 1"))
        repository.addTask(TodoModel(id = "2", title = "Task 2"))
        repository.addTask(TodoModel(id = "3", title = "Task 3"))

        val result = useCase(listOf("1", "3"))

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals(1, repository.getTasks().size)
        assertEquals("2", repository.getTasks()[0].id)
    }

    @Test
    fun deleteAllTasks_whenNullIsPassed() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Task 1"))
        repository.addTask(TodoModel(id = "2", title = "Task 2"))

        val result = useCase(null)

        assertTrue(result is RepoResultWrapper.Success)
        assertTrue(repository.getTasks().isEmpty())
    }

    @Test
    fun delete_returnsErrorWhenRepositoryFails() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Task 1"))
        repository.shouldReturnError = true

        val result = useCase(listOf("1"))

        assertTrue(result is RepoResultWrapper.Error)
    }

    @Test
    fun deleteEmptyList_doesNothing() = runTest {
        setup()
        repository.addTask(TodoModel(id = "1", title = "Task 1"))

        val result = useCase(emptyList())

        assertTrue(result is RepoResultWrapper.Success)
        assertEquals(1, repository.getTasks().size)
    }
}
