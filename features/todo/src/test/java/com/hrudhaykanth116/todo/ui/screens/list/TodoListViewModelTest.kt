package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.TodoDomainModelMapper
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
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
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        observeTasksUseCase = mock<ObserveTasksUseCase>()
        createTodoTaskUseCase = mock<CreateTodoTaskUseCase>()
        deleteTaskUseCase = mock<DeleteTaskUseCase>()
        networkMonitor = mock<NetworkMonitor>()
        mapper = mock<TodoDomainModelMapper>()

        viewModel = TodoListViewModel(
            observeTasksUseCase,
            createTodoTaskUseCase,
            deleteTaskUseCase,
            networkMonitor,
            mapper,
            dispatcher
        )
    }

    @Test
    fun `Test text field value change updates state correctly`() = runTest {

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
    fun `filter event updates selected filter in state`() = runTest {
        val filterCategory = "Work"
        viewModel.processEvent(TodoListScreenEvent.FilterCategory(filterCategory))
        assertEquals(filterCategory, viewModel.contentStateOrDefault.selectedFilter)
    }

    @Test
    fun `sort event updates sort item in state`() = runTest {
        val sortItem = com.hrudhaykanth116.todo.data.models.TodoListScreenSortItem.PRIORITY
        viewModel.processEvent(TodoListScreenEvent.SortOptionSelected(sortItem))
        assertEquals(sortItem, viewModel.contentStateOrDefault.sortItem)
    }

    @Test
    fun `create todo event calls use case and updates state`() = runTest {

        whenever(createTodoTaskUseCase.invoke(any())).thenReturn(RepoResultWrapper.Success(Unit))

        val todoTitle = "New Task"
        viewModel.processEvent(TodoListScreenEvent.CreateTodoTask(todoTitle))

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(TextFieldValue(), viewModel.contentStateOrDefault.todoTitle)
        verify(createTodoTaskUseCase).invoke(any())
    }

    @Test
    fun `remove tasks event calls delete use case`() = runTest {
        val idsToDelete = listOf("1", "2")
        viewModel.processEvent(TodoListScreenEvent.RemoveTasks(idsToDelete))

        dispatcher.scheduler.advanceUntilIdle()

        verify(deleteTaskUseCase).invoke(idsToDelete)
    }

}