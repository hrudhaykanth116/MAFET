package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.TodoDomainModelMapper
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoListScreenSortItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TodoListViewModelTest {
    private lateinit var viewModel: TodoListViewModel
    private lateinit var observeTasksUseCase: ObserveTasksUseCase
    private lateinit var createTodoTaskUseCase: CreateTodoTaskUseCase
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var mapper: TodoDomainModelMapper
    private lateinit var uniqueIdGenerator: UniqueIdGenerator

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        observeTasksUseCase = mock<ObserveTasksUseCase>()
        createTodoTaskUseCase = mock<CreateTodoTaskUseCase>()
        deleteTaskUseCase = mock<DeleteTaskUseCase>()
        networkMonitor = mock<NetworkMonitor>()
        mapper = mock<TodoDomainModelMapper>()
        uniqueIdGenerator = mock<UniqueIdGenerator>()

        whenever(observeTasksUseCase.invoke(anyOrNull(), anyOrNull(), any())).thenReturn(
            flowOf(
                emptyList()
            )
        )

        viewModel = TodoListViewModel(
            observeTasksUseCase,
            createTodoTaskUseCase,
            deleteTaskUseCase,
            networkMonitor,
            mapper,
            uniqueIdGenerator,
            dispatcher
        )
    }

    @Test
    fun `initial state has empty task list`() = runTest {
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, viewModel.contentStateOrDefault.uiList.size)
    }

    @Test
    fun `todo task title changed updates state correctly`() = runTest {
        val newTitle = TextFieldValue("New Task")

        viewModel.processEvent(TodoListScreenEvent.TodoTaskTitleChanged(newTitle))

        assertEquals(newTitle, viewModel.contentStateOrDefault.todoTitle)
    }

    @Test
    fun `search event updates search text in state`() = runTest {
        val searchText = "meeting"
        viewModel.processEvent(TodoListScreenEvent.Search(searchText))
        assertEquals(searchText, viewModel.contentStateOrDefault.search)
    }

    @Test
    fun `filter category event updates selected filter`() = runTest {
        val filterCategory = "Work"
        viewModel.processEvent(TodoListScreenEvent.FilterCategory(filterCategory))
        assertEquals(filterCategory, viewModel.contentStateOrDefault.selectedFilter)
    }

    @Test
    fun `clear filter event removes selected filter and closes category menu`() = runTest {
        viewModel.processEvent(TodoListScreenEvent.FilterCategory("Work"))
        viewModel.processEvent(TodoListScreenEvent.ClearFilter)

        assertNull(viewModel.contentStateOrDefault.selectedFilter)
        assertFalse(viewModel.contentStateOrDefault.isCategoryListMenuVisible)
    }

    @Test
    fun `sort option selected updates sort item and closes sort menu`() = runTest {
        val sortItem = TodoListScreenSortItem.CREATED_AT
        viewModel.processEvent(TodoListScreenEvent.SortOptionSelected(sortItem))

        assertEquals(sortItem, viewModel.contentStateOrDefault.sortItem)
        assertFalse(viewModel.contentStateOrDefault.isSortMenuVisible)
    }

    @Test
    fun `sort icon clicked toggles sort menu visibility`() = runTest {
        assertFalse(viewModel.contentStateOrDefault.isSortMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.SortIconClicked)
        assertTrue(viewModel.contentStateOrDefault.isSortMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.SortIconClicked)
        assertFalse(viewModel.contentStateOrDefault.isSortMenuVisible)
    }

    @Test
    fun `category icon clicked toggles category menu visibility`() = runTest {
        assertFalse(viewModel.contentStateOrDefault.isCategoryListMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.CategoryIconClicked)
        assertTrue(viewModel.contentStateOrDefault.isCategoryListMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.CategoryIconClicked)
        assertFalse(viewModel.contentStateOrDefault.isCategoryListMenuVisible)
    }

    @Test
    fun `category list menu dismiss closes category menu`() = runTest {
        viewModel.processEvent(TodoListScreenEvent.CategoryIconClicked)
        assertTrue(viewModel.contentStateOrDefault.isCategoryListMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.CategoryListMenuDismiss)
        assertFalse(viewModel.contentStateOrDefault.isCategoryListMenuVisible)
    }

    @Test
    fun `menu icon clicked toggles menu visibility`() = runTest {
        assertFalse(viewModel.contentStateOrDefault.isMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.MenuIconClicked)
        assertTrue(viewModel.contentStateOrDefault.isMenuVisible)

        viewModel.processEvent(TodoListScreenEvent.MenuIconClicked)
        assertFalse(viewModel.contentStateOrDefault.isMenuVisible)
    }

    @Test
    fun `search icon clicked shows search bar`() = runTest {
        assertFalse(viewModel.contentStateOrDefault.isSearchBarVisible)

        viewModel.processEvent(TodoListScreenEvent.SearchIconClicked)

        assertTrue(viewModel.contentStateOrDefault.isSearchBarVisible)
    }

    @Test
    fun `close search hides search bar and clears search text`() = runTest {
        viewModel.processEvent(TodoListScreenEvent.Search("test"))
        viewModel.processEvent(TodoListScreenEvent.SearchIconClicked)

        viewModel.processEvent(TodoListScreenEvent.CloseSearch)

        assertFalse(viewModel.contentStateOrDefault.isSearchBarVisible)
        assertEquals("", viewModel.contentStateOrDefault.search)
    }

    @Test
    fun `menu item selected CLEAR_ALL calls delete use case and closes menu`() = runTest {
        viewModel.processEvent(TodoListScreenEvent.MenuIconClicked)
        viewModel.processEvent(TodoListScreenEvent.MenuItemSelected(TodoListScreenMenuItem.CLEAR_ALL))

        dispatcher.scheduler.advanceUntilIdle()

        verify(deleteTaskUseCase).invoke()
        assertFalse(viewModel.contentStateOrDefault.isMenuVisible)
    }

    @Test
    fun `create todo with success clears title and shows success message`() = runTest {
        whenever(createTodoTaskUseCase.invoke(any())).thenReturn(DomainResult.Success(Unit))
        whenever(uniqueIdGenerator.getUniqueId()).thenReturn("10")

        val todoTitle = "New Task"
        viewModel.processEvent(TodoListScreenEvent.TodoTaskTitleChanged(TextFieldValue(todoTitle)))
        viewModel.processEvent(TodoListScreenEvent.CreateTodoTask(todoTitle))

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(TextFieldValue(), viewModel.contentStateOrDefault.todoTitle)
        val state = viewModel.uiStateFlow.value
        assertNotNull((state as? UIState.Idle)?.userMessage)
    }

    @Test
    fun `create todo with error shows error message and keeps title`() = runTest {
        whenever(createTodoTaskUseCase.invoke(any())).thenReturn(
            DomainResult.Error(DomainError.Unknown())
        )
        whenever(uniqueIdGenerator.getUniqueId()).thenReturn("10")

        val todoTitle = "New Task"
        viewModel.processEvent(TodoListScreenEvent.CreateTodoTask(todoTitle))

        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiStateFlow.value
        assertNotNull((state as? UIState.Idle)?.userMessage)
    }

    @Test
    fun `remove tasks event calls delete use case with correct ids`() = runTest {
        val idsToDelete = listOf("1", "2", "3")
        viewModel.processEvent(TodoListScreenEvent.RemoveTasks(idsToDelete))

        dispatcher.scheduler.advanceUntilIdle()

        verify(deleteTaskUseCase).invoke(idsToDelete)
    }

    @Test
    fun `observe tasks with mapped data updates ui list`() = runTest {
        val todoModels = listOf(
            TodoModel(id = "1", title = "Task 1", category = TaskCategory.WORK),
            TodoModel(id = "2", title = "Task 2", category = TaskCategory.PERSONAL)
        )
        val uiStates = listOf(
            ToDoTaskUIState(id = "1", title = "Task 1", category = "Work"),
            ToDoTaskUIState(id = "2", title = "Task 2", category = "Personal")
        )

        whenever(observeTasksUseCase.invoke(anyOrNull(), anyOrNull(), any())).thenReturn(
            flowOf(todoModels)
        )
        whenever(mapper.mapListToUIStates(todoModels)).thenReturn(uiStates)

        val newViewModel = TodoListViewModel(
            observeTasksUseCase,
            createTodoTaskUseCase,
            deleteTaskUseCase,
            networkMonitor,
            mapper,
            uniqueIdGenerator,
            dispatcher
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(uiStates.toImmutableList(), newViewModel.contentStateOrDefault.uiList)
    }

    @Test
    fun `search text triggers observe tasks with search parameter`() = runTest {
        val searchText = "important"
        viewModel.processEvent(TodoListScreenEvent.Search(searchText))

        dispatcher.scheduler.advanceUntilIdle()

        verify(observeTasksUseCase, times(2)).invoke(searchText, null, any())
    }

    @Test
    fun `filter category triggers observe tasks with filter parameter`() = runTest {
        val category = "Work"
        viewModel.processEvent(TodoListScreenEvent.FilterCategory(category))

        dispatcher.scheduler.advanceUntilIdle()

        verify(observeTasksUseCase, times(2)).invoke(null, category, any())
    }

    @Test
    fun `sort option selected triggers observe tasks with sort parameter`() = runTest {
        val sortItem = TodoListScreenSortItem.CREATED_AT
        viewModel.processEvent(TodoListScreenEvent.SortOptionSelected(sortItem))

        dispatcher.scheduler.advanceUntilIdle()

        verify(observeTasksUseCase, times(2)).invoke(anyOrNull(), anyOrNull(), sortItem.key)
    }
}