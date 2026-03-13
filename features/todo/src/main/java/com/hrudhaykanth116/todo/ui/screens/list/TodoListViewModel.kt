package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.di.MainDispatcher
import com.hrudhaykanth116.core.common.ui.models.toErrorMessage
import com.hrudhaykanth116.core.common.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.TodoDomainModelMapper
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoListScreenSortItem
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val networkMonitor: NetworkMonitor,
    private val mapper: TodoDomainModelMapper,
    private val uniqueIdGenerator: UniqueIdGenerator,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : UIStateViewModel<TodoListUIState, TodoListScreenEvent, CreateTodoEffect>(
    initialState = UIState.Idle(TodoListUIState()),
    defaultState = TodoListUIState(),
    networkMonitor = networkMonitor,
) {

    init {
        initializeData()
    }

    override fun initializeData() {
        observeTasks()
        observeCategories()
    }

    private fun observeTasks() {
        viewModelScope.launch(dispatcher) {
            contentStateFlow.distinctUntilChanged().flatMapLatest { state: TodoListUIState ->
                observeTasksUseCase(
                    state.search,
                    state.selectedFilter,
                    state.sortItem.key
                )
            }.collectLatest { todoDomainModelList: List<TodoModel> ->
                val toDoTaskUIStates: List<ToDoTaskUIState> =
                    mapper.mapListToUIStates(todoDomainModelList)
                setTasksList(toDoTaskUIStates)
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch(dispatcher) {
            observeTasksUseCase(
                null,
                null,
                contentStateOrDefault.sortItem.key
            ).collectLatest { todoModelList ->
                val categories = todoModelList.map { it.category.key }.toSet()
                setState {
                    UIState.Idle(
                        contentState?.copy(
                            filterOptions = categories.toImmutableSet()
                        )
                    )
                }
            }
        }
    }

    private fun setTasksList(
        toDoTaskUIStates: List<ToDoTaskUIState>,
    ) {

        setState {
            UIState.Idle(
                contentState?.copy(
                    uiList = toDoTaskUIStates.toImmutableList(),
                )
            )
        }

    }


    private fun removeTaskItems(taskIdsToDelete: List<String>) {
        viewModelScope.launch(dispatcher) {
            deleteTaskUseCase(taskIdsToDelete)
        }
    }

    override fun processEvent(event: TodoListScreenEvent) {
        when (event) {
            is TodoListScreenEvent.RemoveTasks -> removeTaskItems(event.taskIdsToDelete)
            is TodoListScreenEvent.TodoTaskTitleChanged -> onTodoTaskTitleChanged(event.textFieldValue)
            is TodoListScreenEvent.CreateTodoTask -> createTodoTask(event.taskTitle)
            is TodoListScreenEvent.FilterCategory -> onFilterSelected(event.filterCategory)
            is TodoListScreenEvent.ClearFilter -> onFilterSelected(null)
            is TodoListScreenEvent.Search -> setSearchText(event.searchText)
            TodoListScreenEvent.CategoryIconClicked -> onCategoryIconClicked()
            TodoListScreenEvent.CategoryListMenuDismiss -> onCategoryMenuDismiss()
            TodoListScreenEvent.MenuIconClicked -> onMenuIconClicked()
            TodoListScreenEvent.SearchIconClicked -> onSearchIconClicked()
            TodoListScreenEvent.CloseSearch -> onCloseSearch()
            is TodoListScreenEvent.MenuItemSelected -> onMenuItemSelected(event.menuItem)
            TodoListScreenEvent.SortIconClicked -> onSortIconClicked()
            is TodoListScreenEvent.SortOptionSelected -> onSortOptionClicked(event.sortItem)
        }
    }

    private fun onSortIconClicked() {
        setState {
            UIState.Idle(contentState?.copy(isSortMenuVisible = !contentState.isSortMenuVisible))
        }
    }

    private fun onSortOptionClicked(sortItem: TodoListScreenSortItem) {

        setState {
            UIState.Idle(
                contentState?.copy(
                    sortItem = sortItem,
                    isSortMenuVisible = false
                )
            )
        }
    }

    private fun onMenuItemSelected(menuItem: TodoListScreenMenuItem) {
        setState {
            UIState.Idle(contentState?.copy(isMenuVisible = false))
        }
        when (menuItem) {
            TodoListScreenMenuItem.CLEAR_ALL -> deleteAllTasks()
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(dispatcher) {
            deleteTaskUseCase()
        }
    }

    private fun onSearchIconClicked() {
        setState {
            UIState.Idle(contentState?.copy(isSearchBarVisible = true))
        }
    }

    private fun onCloseSearch() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    isSearchBarVisible = false,
                    search = ""
                )
            )
        }
    }

    private fun onMenuIconClicked() {
        setState {
            UIState.Idle(contentState?.copy(isMenuVisible = !contentState.isMenuVisible))
        }
    }

    private fun onCategoryIconClicked() {
        setState {
            UIState.Idle(contentState?.copy(isCategoryListMenuVisible = !contentState.isCategoryListMenuVisible))
        }
    }

    fun onCategoryMenuDismiss() {
        setState {
            UIState.Idle(contentState?.copy(isCategoryListMenuVisible = false))
        }
    }

    private fun createTodoTask(taskTitle: String) {
        viewModelScope.launch(dispatcher) {
            val result: RepoResultWrapper<Unit> = createTodoTaskUseCase(
                TodoModel(
                    id = uniqueIdGenerator.getUniqueId(),
                    title = taskTitle,
                )
            )
            when (result) {
                is RepoResultWrapper.Error -> {
                    setState {
                        UIState.Idle(
                            contentStateOrDefault,
                            userMessage = R.string.todo_error_generic.toErrorMessage()
                        )
                    }
                }

                is RepoResultWrapper.Success -> {
                    setState {
                        UIState.Idle(
                            contentStateOrDefault.copy(todoTitle = TextFieldValue()),
                            userMessage = R.string.todo_success_task_created.toSuccessMessage()
                        )
                    }
                }
            }
        }
    }

    private fun onTodoTaskTitleChanged(textFieldValue: TextFieldValue) {

        setState {
            UIState.Idle(
                contentState?.copy(
                    todoTitle = textFieldValue
                )
            )
        }
    }

    private fun onFilterSelected(filter: String?) {
        setState {
            UIState.Idle(
                contentState?.copy(
                    selectedFilter = filter,
                    isCategoryListMenuVisible = false,
                )
            )
        }
    }

    private fun setSearchText(search: String) {
        setState {
            UIState.Idle(
                contentState?.copy(
                    search = search,
                )
            )
        }
    }
}