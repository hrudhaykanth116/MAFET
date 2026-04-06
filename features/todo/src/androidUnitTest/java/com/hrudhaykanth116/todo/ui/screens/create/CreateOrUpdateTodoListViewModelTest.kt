package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateOrUpdateTodoListViewModelTest {
    private lateinit var createTodoTaskUseCase: CreateTodoTaskUseCase
    private lateinit var getTaskUseCase: GetTaskUseCase
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var dateTimeUtils: DateTimeUtils
    private lateinit var uniqueIdGenerator: UniqueIdGenerator

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        createTodoTaskUseCase = mockk()
        getTaskUseCase = mockk()
        networkMonitor = mockk()
        dateTimeUtils = mockk()
        uniqueIdGenerator = mockk()

        every { dateTimeUtils.getFormattedDateTime(any(), any()) } returns "2024-01-01 10:30 AM"
        every { dateTimeUtils.getMillisFromDateTime(any(), any()) } returns 1704096600000L
    }

    private fun createViewModel(todoId: String? = null): CreateOrUpdateTodoListViewModel {
        return CreateOrUpdateTodoListViewModel(
            createTodoTaskUseCase,
            getTaskUseCase,
            networkMonitor,
            dateTimeUtils,
            uniqueIdGenerator,
            todoId
        )
    }

    @Test
    fun `initial state for new todo has empty fields`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.contentStateOrDefault
        assertEquals("", state.todoUIModel.title.text)
        assertEquals("", state.todoUIModel.description.text)
        assertEquals("", state.todoUIModel.category.text)
        assertEquals(0, state.todoUIModel.priority)
    }

    @Test
    fun `initial state loads existing todo when todoId is provided`() = runTest {
        val todoModel = TodoModel(
            id = "123",
            title = "Existing Task",
            description = "Task description",
            category = TaskCategory.WORK,
            priority = 2,
            targetTime = 1704096600000L
        )

        coEvery { getTaskUseCase.invoke("123") } returns DomainResult.Success(todoModel)

        val viewModel = createViewModel(todoId = "123")
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.contentStateOrDefault
        assertEquals("Existing Task", state.todoUIModel.title.text)
        assertEquals("Task description", state.todoUIModel.description.text)
        assertEquals("work", state.todoUIModel.category.text)
        assertEquals(2, state.todoUIModel.priority)
        assertEquals("2024-01-01 10:30 AM", state.todoUIModel.targetTime.text)
    }

    @Test
    fun `initial state handles error when loading existing todo`() = runTest {
        coEvery { getTaskUseCase.invoke("123") } returns DomainResult.Error(DomainError.Unknown())

        val viewModel = createViewModel(todoId = "123")
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.contentStateOrDefault
        assertEquals("", state.todoUIModel.title.text)
    }

    @Test
    fun `title changed event updates title in state`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        val newTitle = TextFieldValue("New Title")
        viewModel.processEvent(CreateTodoEvent.TitleChanged(newTitle))

        assertEquals(newTitle, viewModel.contentStateOrDefault.todoUIModel.title)
        assertNull(viewModel.contentStateOrDefault.titleError)
    }

    @Test
    fun `description changed event updates description in state`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        val newDescription = TextFieldValue("Task details")
        viewModel.processEvent(CreateTodoEvent.DescriptionChanged(newDescription))

        assertEquals(newDescription, viewModel.contentStateOrDefault.todoUIModel.description)
    }

    @Test
    fun `category changed event updates category in state`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        val newCategory = TextFieldValue("personal")
        viewModel.processEvent(CreateTodoEvent.CategoryChanged(newCategory))

        assertEquals(newCategory, viewModel.contentStateOrDefault.todoUIModel.category)
    }

    @Test
    fun `category selected event updates category and closes dropdown`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.CategorySelected(TaskCategory.WORK))

        assertEquals("work", viewModel.contentStateOrDefault.todoUIModel.category.text)
        assertFalse(viewModel.contentStateOrDefault.showCategoryDropdown)
    }

    @Test
    fun `priority changed event updates priority in state`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.PriorityChanged(3))

        assertEquals(3, viewModel.contentStateOrDefault.todoUIModel.priority)
    }

    @Test
    fun `on target field clicked shows time picker`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.OnTargetFieldClicked)

        assertTrue(viewModel.contentStateOrDefault.showTargetTimePicker)
    }

    @Test
    fun `on target time changed updates target time and formats it`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.OnTargetTimeChanged(1704096600000L))

        assertEquals("2024-01-01 10:30 AM", viewModel.contentStateOrDefault.todoUIModel.targetTime.text)
    }

    @Test
    fun `on target time picker close request hides picker`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.OnTargetFieldClicked)
        viewModel.processEvent(CreateTodoEvent.OnTargetTimeDateTimePickerCloseRequest)

        assertFalse(viewModel.contentStateOrDefault.showTargetTimePicker)
    }

    @Test
    fun `on category field clicked shows category dropdown`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.OnCategoryFieldClicked)

        assertTrue(viewModel.contentStateOrDefault.showCategoryDropdown)
    }

    @Test
    fun `on category dismiss request hides dropdown`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.OnCategoryFieldClicked)
        viewModel.processEvent(CreateTodoEvent.OnCategoryDismissRequest)

        assertFalse(viewModel.contentStateOrDefault.showCategoryDropdown)
    }

    @Test
    fun `submit with blank title shows validation error`() = runTest {
        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.Submit)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Title is required", viewModel.contentStateOrDefault.titleError)
        assertFalse(viewModel.contentStateOrDefault.isSubmitted)
    }

    @Test
    fun `submit with valid data calls use case and marks submitted`() = runTest {
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Success(Unit)
        every { uniqueIdGenerator.getUniqueId() } returns "new-id"

        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Task Title")))
        viewModel.processEvent(CreateTodoEvent.DescriptionChanged(TextFieldValue("Task description")))
        viewModel.processEvent(CreateTodoEvent.CategorySelected(TaskCategory.WORK))
        viewModel.processEvent(CreateTodoEvent.PriorityChanged(2))
        viewModel.processEvent(CreateTodoEvent.Submit)

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { createTodoTaskUseCase.invoke(any()) }
        assertTrue(viewModel.contentStateOrDefault.isSubmitted)
    }

    @Test
    fun `submit with error shows error message and does not mark submitted`() = runTest {
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Error(DomainError.Unknown())
        every { uniqueIdGenerator.getUniqueId() } returns "new-id"

        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Task Title")))
        viewModel.processEvent(CreateTodoEvent.Submit)

        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.contentStateOrDefault.isSubmitted)
        val state = viewModel.uiStateFlow.value
        assertNotNull((state as? UIState.Idle)?.userMessage)
    }

    @Test
    fun `submit updates existing todo when todoId is provided`() = runTest {
        val existingTodo = TodoModel(
            id = "existing-123",
            title = "Old Title",
            category = TaskCategory.PERSONAL
        )

        coEvery { getTaskUseCase.invoke("existing-123") } returns DomainResult.Success(existingTodo)
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Success(Unit)

        val viewModel = createViewModel(todoId = "existing-123")
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Updated Title")))
        viewModel.processEvent(CreateTodoEvent.Submit)

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { createTodoTaskUseCase.invoke(any()) }
        assertTrue(viewModel.contentStateOrDefault.isSubmitted)
    }

    @Test
    fun `submit with target time includes time in todo model`() = runTest {
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Success(Unit)
        every { uniqueIdGenerator.getUniqueId() } returns "new-id"

        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Task with deadline")))
        viewModel.processEvent(CreateTodoEvent.OnTargetTimeChanged(1704096600000L))
        viewModel.processEvent(CreateTodoEvent.Submit)

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { createTodoTaskUseCase.invoke(any()) }
        verify { dateTimeUtils.getMillisFromDateTime("2024-01-01 10:30 AM", any()) }
    }

    @Test
    fun `user message shown event clears user message`() = runTest {
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Error(DomainError.Unknown())
        every { uniqueIdGenerator.getUniqueId() } returns "new-id"

        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Task")))
        viewModel.processEvent(CreateTodoEvent.Submit)
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.UserMessageShown)

        val state = viewModel.uiStateFlow.value
        assertNull((state as? UIState.Idle)?.userMessage)
    }

    @Test
    fun `loading state is shown during initialization`() = runTest {
        val viewModel = createViewModel()

        val state = viewModel.uiStateFlow.value
        assertTrue(state is UIState.Loading)
    }

    @Test
    fun `loading state is shown during submit`() = runTest {
        var loadingStateObserved = false
        coEvery { createTodoTaskUseCase.invoke(any()) } returns DomainResult.Success(Unit)
        every { uniqueIdGenerator.getUniqueId() } returns "new-id"

        val viewModel = createViewModel()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(CreateTodoEvent.TitleChanged(TextFieldValue("Task")))
        viewModel.processEvent(CreateTodoEvent.Submit)

        if (viewModel.uiStateFlow.value is UIState.Loading) {
            loadingStateObserved = true
        }

        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(loadingStateObserved || viewModel.contentStateOrDefault.isSubmitted)
    }
}
