package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.udf.UIStateViewModel2
import com.hrudhaykanth116.core.ui.models.UIState2
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.toState
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenMenuItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenSortItem
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : UIStateViewModel2<TodoListUIState, TodoListScreenEvent, CreateTodoEffect>(
    TodoListUIState(UIState2.Loading)
) {

    init {

        val result: Flow<List<TodoModel>> = uiStateFlow.mapLatest {
            Triple(it.search, it.selectedFilter, it.sortItem)
        }.distinctUntilChanged().flatMapLatest {
            observeTasksUseCase(it.first, it.second, it.third.displayName)
        }

        uiStateFlow.mapLatest {
            it.sortItem
        }.distinctUntilChanged().mapLatest {

        }

        viewModelScope.launch {
            uiStateFlow.mapLatest {
                // TODO: Dont observe sort here which will trigger db query
                Triple(it.search, it.selectedFilter, it.sortItem)
            }.distinctUntilChanged().flatMapLatest {
                observeTasksUseCase(it.first, it.second, it.third.key)
            }.collectLatest { todoDomainModelList ->
                val toDoTaskUIStates = todoDomainModelList.map { it.toState() }.sortedBy {
                    it.data.priority
                }
                setTasksList(toDoTaskUIStates)
            }
        }

        viewModelScope.launch {
            observeTasksUseCase("", "", uiState.sortItem.key).collectLatest { todoModelList ->

                // TODO: Get categories list without groupBY. Using domain model ??
                val categories = todoModelList.groupBy { todoModel ->
                    todoModel.category
                }.keys

                setState {
                    copy(
                        filterOptions = categories.toImmutableSet()
                    )
                }
            }
        }
    }

    private fun setTasksList(
        toDoTaskUIStates: List<ToDoTaskUIState>,
    ) {

        setState {
            copy(
                uiList = toDoTaskUIStates.toImmutableList(),
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

    private fun onSortIconClicked() {
        setState {
            copy(
                isSortMenuVisible = !isSortMenuVisible
            )
        }
    }

    private fun onSortOptionClicked(sortItem: TodoListScreenSortItem) {
        setState {
            copy(
                sortItem = sortItem,
                isSortMenuVisible = false
            )
        }
    }

    private fun onMenuItemSelected(menuItem: TodoListScreenMenuItem) {
        setState {
            copy(
                isMenuVisible = false
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
            copy(
                isSearchBarVisible = true
            )
        }
    }

    private fun onMenuIconClicked() {
        setState {
            // TODO: Recheck this
            copy(
                isMenuVisible = !isMenuVisible
            )
        }
    }


    private fun onCategoryIconClicked() {
        setState {
            copy(
                isCategoryListMenuVisible = !isCategoryListMenuVisible
            )
        }
    }

    fun onCategoryMenuDismiss() {
        setState {
            copy(
                isCategoryListMenuVisible = false
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

                is DomainState.LoadingDomainState -> {

                }
            }
        }
    }

    private fun onTodoTaskTitleChanged(textFieldValue: TextFieldValue) {
        setState {
            copy(
                todoTitle = textFieldValue
            )
        }
    }

    private fun onFilterSelected(filter: String?) {
        setState {
            copy(
                selectedFilter = filter,
                isCategoryListMenuVisible = false,
            )
        }
    }

    private fun setSearchText(search: String) {
        setState {
            copy(
                search = search,
            )
        }
    }

}