package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ObserveTasksUseCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var useCase: ObserveTasksUseCase

    @Before
    fun setup() {
        repository = FakeTodoRepository()
        useCase = ObserveTasksUseCase(repository)
    }

    @Test
    fun `observe returns all tasks when no filter`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Task 1", category = TaskCategory.WORK))
        repository.addTask(TodoModel(id = "2", title = "Task 2", category = TaskCategory.PERSONAL))

        val tasks = useCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(2, tasks.size)
    }

    @Test
    fun `observe filters by category`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Work task", category = TaskCategory.WORK))
        repository.addTask(TodoModel(id = "2", title = "Personal task", category = TaskCategory.PERSONAL))
        repository.addTask(TodoModel(id = "3", title = "Another work", category = TaskCategory.WORK))

        val tasks = useCase(null, TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(2, tasks.size)
        assertTrue(tasks.all { it.category == TaskCategory.WORK })
    }

    @Test
    fun `observe filters by search text`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Buy groceries"))
        repository.addTask(TodoModel(id = "2", title = "Call mom"))
        repository.addTask(TodoModel(id = "3", title = "Buy birthday gift"))

        val tasks = useCase("Buy", null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(2, tasks.size)
    }

    @Test
    fun `observe sorts by priority descending`() = runTest {
        repository.addTask(TodoModel(id = "1", title = "Low", priority = 1))
        repository.addTask(TodoModel(id = "2", title = "High", priority = 5))
        repository.addTask(TodoModel(id = "3", title = "Medium", priority = 3))

        val tasks = useCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals("High", tasks[0].title)
        assertEquals("Medium", tasks[1].title)
        assertEquals("Low", tasks[2].title)
    }

    @Test
    fun `observe returns empty list when no tasks`() = runTest {
        val tasks = useCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()

        assertEquals(0, tasks.size)
    }
}
