package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.todo.data.repositories.FakeTodoRepository
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.testutils.FakeNotificationScheduler
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.domain.use_cases.UpdateTodoTaskUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TodoEdgeCaseTest {

    private lateinit var repository: FakeTodoRepository
    private lateinit var createUseCase: CreateTodoTaskUseCase
    private lateinit var updateUseCase: UpdateTodoTaskUseCase
    private lateinit var getUseCase: GetTaskUseCase
    private lateinit var observeUseCase: ObserveTasksUseCase

    @BeforeTest
    fun setup() {
        repository = FakeTodoRepository()
        createUseCase = CreateTodoTaskUseCase(repository, FakeNotificationScheduler())
        updateUseCase = UpdateTodoTaskUseCase(repository)
        getUseCase = GetTaskUseCase(repository)
        observeUseCase = ObserveTasksUseCase(repository)
    }

    @Test
    fun createTask_withVeryLongTitle_succeeds() = runTest {
        val longTitle = "A".repeat(500)
        val task = TodoModel(id = "long-1", title = longTitle)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("long-1")
        assertTrue(saved is DomainResult.Success)
        assertEquals(500, (saved as DomainResult.Success).data.title.length)
    }

    @Test
    fun createTask_withVeryLongDescription_succeeds() = runTest {
        val longDescription = "B".repeat(2000)
        val task = TodoModel(id = "desc-1", title = "Task", description = longDescription)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("desc-1")
        assertEquals(2000, (saved as DomainResult.Success).data.description.length)
    }

    @Test
    fun createTask_withSpecialCharactersInTitle_succeeds() = runTest {
        val specialTitle = "Task with émojis 🎉 and spëcial çhàracters @#\$%^&*()"
        val task = TodoModel(id = "special-1", title = specialTitle)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("special-1")
        assertEquals(specialTitle, (saved as DomainResult.Success).data.title)
    }

    @Test
    fun createTask_withUnicodeCharacters_succeeds() = runTest {
        val unicodeTitle = "日本語タスク 中文任务 العربية"
        val task = TodoModel(id = "unicode-1", title = unicodeTitle)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("unicode-1")
        assertEquals(unicodeTitle, (saved as DomainResult.Success).data.title)
    }

    @Test
    fun createTask_withNewlinesInDescription_succeeds() = runTest {
        val multilineDesc = "Line 1\nLine 2\nLine 3\n\nLine 5"
        val task = TodoModel(id = "newline-1", title = "Task", description = multilineDesc)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("newline-1")
        assertEquals(multilineDesc, (saved as DomainResult.Success).data.description)
    }

    @Test
    fun createTask_withPriorityAtMinBoundary_succeeds() = runTest {
        val task = TodoModel(id = "min-1", title = "Lowest priority", priority = TodoDefaults.PRIORITY_MIN)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("min-1")
        assertEquals(TodoDefaults.PRIORITY_MIN, (saved as DomainResult.Success).data.priority)
    }

    @Test
    fun createTask_withPriorityAtMaxBoundary_succeeds() = runTest {
        val task = TodoModel(id = "max-1", title = "Highest priority", priority = TodoDefaults.PRIORITY_MAX)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("max-1")
        assertEquals(TodoDefaults.PRIORITY_MAX, (saved as DomainResult.Success).data.priority)
    }

    @Test
    fun createTask_withZeroPriority_succeeds() = runTest {
        val task = TodoModel(id = "zero-1", title = "Zero priority", priority = 0)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
    }

    @Test
    fun createTask_withNegativePriority_succeeds() = runTest {
        val task = TodoModel(id = "neg-1", title = "Negative priority", priority = -1)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
    }

    @Test
    fun createTask_withVeryLargeTargetTime_succeeds() = runTest {
        val farFuture = Long.MAX_VALUE - 1000
        val task = TodoModel(id = "future-1", title = "Far future task", targetTime = farFuture)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("future-1")
        assertEquals(farFuture, (saved as DomainResult.Success).data.targetTime)
    }

    @Test
    fun createTask_withZeroTargetTime_succeeds() = runTest {
        val task = TodoModel(id = "epoch-1", title = "Epoch task", targetTime = 0L)

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("epoch-1")
        assertEquals(0L, (saved as DomainResult.Success).data.targetTime)
    }

    @Test
    fun searchWithSpecialCharacters_works() = runTest {
        createUseCase(TodoModel(id = "1", title = "Task with @mention"))
        createUseCase(TodoModel(id = "2", title = "Task with #hashtag"))
        createUseCase(TodoModel(id = "3", title = "Regular task"))

        val mentionResults = observeUseCase("@mention", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(1, mentionResults.size)

        val hashResults = observeUseCase("#hashtag", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(1, hashResults.size)
    }

    @Test
    fun searchWithEmptyString_returnsAll() = runTest {
        createUseCase(TodoModel(id = "1", title = "Task 1"))
        createUseCase(TodoModel(id = "2", title = "Task 2"))

        val results = observeUseCase("", null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(2, results.size)
    }

    @Test
    fun updateTask_withAllFieldsChanged_succeeds() = runTest {
        val original = TodoModel(
            id = "upd-1",
            title = "Original",
            description = "Original desc",
            category = TaskCategory.WORK,
            priority = 1,
            targetTime = 1000L
        )
        createUseCase(original)

        val updated = TodoModel(
            id = "upd-1",
            title = "Updated",
            description = "Updated desc",
            category = TaskCategory.PERSONAL,
            priority = 5,
            targetTime = 2000L
        )
        updateUseCase(updated)

        val saved = getUseCase("upd-1")
        assertTrue(saved is DomainResult.Success)
        val data = (saved as DomainResult.Success).data
        assertEquals("Updated", data.title)
        assertEquals("Updated desc", data.description)
        assertEquals(TaskCategory.PERSONAL, data.category)
        assertEquals(5, data.priority)
        assertEquals(2000L, data.targetTime)
    }

    @Test
    fun createManyTasks_succeeds() = runTest {
        repeat(100) { index ->
            val task = TodoModel(id = "bulk-$index", title = "Task $index")
            createUseCase(task)
        }

        val all = observeUseCase(null, null, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(100, all.size)
    }

    @Test
    fun createTask_withIdContainingSpecialChars_succeeds() = runTest {
        val task = TodoModel(id = "id-with-special_chars.123", title = "Task")

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
        val saved = getUseCase("id-with-special_chars.123")
        assertTrue(saved is DomainResult.Success)
    }

    @Test
    fun createTask_withWhitespaceOnlyDescription_succeeds() = runTest {
        val task = TodoModel(id = "ws-1", title = "Task", description = "   \t\n   ")

        val result = createUseCase(task)

        assertTrue(result is DomainResult.Success)
    }

    @Test
    fun filterByCategory_afterMultipleUpdates_returnsCorrect() = runTest {
        createUseCase(TodoModel(id = "cat-1", title = "Task 1", category = TaskCategory.WORK))

        updateUseCase(TodoModel(id = "cat-1", title = "Task 1", category = TaskCategory.PERSONAL))
        updateUseCase(TodoModel(id = "cat-1", title = "Task 1", category = TaskCategory.SHOPPING))
        updateUseCase(TodoModel(id = "cat-1", title = "Task 1", category = TaskCategory.WORK))

        val workTasks = observeUseCase(null, TaskCategory.WORK.key, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(1, workTasks.size)

        val personalTasks = observeUseCase(null, TaskCategory.PERSONAL.key, TodoDefaults.SORT_BY_PRIORITY).first()
        assertEquals(0, personalTasks.size)
    }
}
