package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenSortItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
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
    private val dateTimeUtils: DateTimeUtils,
) : UIStateViewModel<TodoListUIState, TodoListScreenEvent, CreateTodoEffect>(
    initialState = UIState.Idle(TodoListUIState()),
    defaultState = TodoListUIState(),
    networkMonitor = networkMonitor,
) {

    init {

        viewModelScope.launch {

            contentStateFlow.distinctUntilChanged().flatMapLatest { state: TodoListUIState ->
                observeTasksUseCase(
                    state.search,
                    state.selectedFilter,
                    state.sortItem.key
                )
            }.collectLatest { todoDomainModelList: List<TodoModel> ->


                val toDoTaskUIStates: List<ToDoTaskUIState> =
                    todoDomainModelList.map { todoDomainModel ->
                        ToDoTaskUIState(
                            data = with(todoDomainModel) {
                                TodoUIModel(
                                    id = id,
                                    title = TextFieldValue(title),
                                    description = TextFieldValue(description),
                                    category = TextFieldValue(category),
                                    priority = priority,
                                    targetTime = TextFieldValue(targetTime?.let {
                                        dateTimeUtils.getFormattedDateTime(it)
                                    } ?: ""),
                                )
                            },
                        )
                    }
                setTasksList(toDoTaskUIStates)
            }

        }

        // val result: Flow<List<TodoModel>> =
        //     contentStateFlow.filterNotNull().mapLatest { it: TodoListUIState ->
        //         Triple(it.search, it.selectedFilter, it.sortItem)
        //     }.distinctUntilChanged().flatMapLatest {
        //         observeTasksUseCase(it.first, it.second, it.third.displayName)
        //     }
        //
        // contentStateFlow.filterNotNull().mapLatest {
        //     it.sortItem
        // }.distinctUntilChanged().mapLatest {
        //
        //     observeTasksUseCase(
        //         search = currentContentState.search,
        //         filterCategory = currentContentState.selectedFilter,
        //         sortItem = it.third.displayName
        //     )
        //
        // }
        //
        // viewModelScope.launch {
        //     contentStateFlow.filterNotNull().mapLatest {
        //         // TODO: Dont observe sort here which will trigger db query
        //         Triple(it.search, it.selectedFilter, it.sortItem)
        //     }.distinctUntilChanged().flatMapLatest {
        //         observeTasksUseCase(it.first, it.second, it.third.key)
        //     }.collectLatest { todoDomainModelList ->
        //         val toDoTaskUIStates = todoDomainModelList.map { it.toState() }.sortedBy {
        //             it.data.priority
        //         }
        //         setTasksList(toDoTaskUIStates)
        //     }
        // }

        viewModelScope.launch {
            observeTasksUseCase(
                null,
                null,
                currentContentState.sortItem.key
            ).collectLatest { todoModelList ->

                // TODO: Get categories list without groupBY. Using domain model ??
                val categories = todoModelList.groupBy { todoModel ->
                    todoModel.category
                }.keys

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
        viewModelScope.launch {
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
            is TodoListScreenEvent.MenuItemSelected -> onMenuItemSelected(event.menuItem)
            TodoListScreenEvent.SortIconClicked -> onSortIconClicked()
            is TodoListScreenEvent.SortOptionSelected -> onSortOptionClicked(event.sortItem)
        }
    }

    override fun onRetry() {

    }

    private fun onSortIconClicked() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    isSortMenuVisible = contentState?.isSortMenuVisible != true
                )
            )
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
            UIState.Idle(
                contentState?.copy(
                    isMenuVisible = false
                )
            )
        }
        when (menuItem) {
            TodoListScreenMenuItem.SETTINGS -> {
                // setEffect()
            }

            TodoListScreenMenuItem.CLEAR_ALL -> {
                // deleteTasks()
            }
        }
    }

    private fun deleteTasks() {
        viewModelScope.launch {
            deleteTaskUseCase()
        }
    }

    private fun onSearchIconClicked() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    isSearchBarVisible = true
                )
            )
        }
    }

    private fun onMenuIconClicked() {

        setState {
            UIState.Idle(
                contentState?.copy(
                    isMenuVisible = contentState?.isMenuVisible != true
                )
            )
        }
    }


    private fun onCategoryIconClicked() {

        setState {
            UIState.Idle(
                contentState?.copy(
                    isCategoryListMenuVisible = contentState?.isCategoryListMenuVisible != true
                )
            )
        }
    }

    fun onCategoryMenuDismiss() {
        setState {
            UIState.Idle(
                contentState?.copy(
                    isCategoryListMenuVisible = false
                )
            )
        }
    }

    private fun createTodoTask(taskTitle: String) {
        viewModelScope.launch {
            val result: DomainState<CreateOrUpdateTodoDomainModel> = createTodoTaskUseCase(
                CreateOrUpdateTodoDomainModel(title = taskTitle)
            )
            when (result) {
                is DomainState.ErrorDomainState -> {

                }

                is DomainState.LoadedDomainState -> {
                    onTodoTaskTitleChanged(TextFieldValue())
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