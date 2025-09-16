package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState

private const val TAG = "TodoListScreen"

@Composable
fun TodoListScreen(
    todoListViewModel: TodoListViewModel = hiltViewModel(),
    navigateToCreateScreen: () -> Unit,
    onItemClicked: (TodoUIModel) -> Unit,
    onBackClicked: () -> Unit = {},
) {
    Logger.d(TAG, "TodoListScreen: ")

    LaunchedEffect(Unit) {
        todoListViewModel.initialize()
    }

    val uiState: State<TodoListUIState?> =
        todoListViewModel.contentStateFlow.collectAsState(null)

    // val list by todoViewModel.todoList.observeAsState(listOf())

    val state = uiState.value ?: TodoListUIState()
    TodoListScreenUI(
        modifier = Modifier,
        uiState = state,
        onRemoveTask = { id: String ->
            todoListViewModel.processEvent(TodoListScreenEvent.RemoveTasks(listOf(id)))
        },
        onItemClicked = onItemClicked,
        onTodoTitleChanged = {
            todoListViewModel.processEvent(TodoListScreenEvent.TodoTaskTitleChanged(it))
        },
        onCreateBtnClicked = {
            val todoTitle = state.todoTitle.text
            if (todoTitle.isEmpty()) {
                navigateToCreateScreen()
            } else {
                todoListViewModel.processEvent(TodoListScreenEvent.CreateTodoTask(todoTitle))
            }
        },
        todoListAppBarCallbacks = TodoListAppBarCallbacks(
            onCategorySelected = {
                todoListViewModel.processEvent(TodoListScreenEvent.FilterCategory(it))
            },
            onClearFilterClicked = {
                todoListViewModel.processEvent(TodoListScreenEvent.ClearFilter)
            },
            onMenuItemSelected = {
                todoListViewModel.processEvent(TodoListScreenEvent.MenuItemSelected(it))
            },
            onCategoriesIconClicked = {
                todoListViewModel.processEvent(TodoListScreenEvent.CategoryIconClicked)
            },
            onCategoriesDismissRequest = {
                todoListViewModel.processEvent(TodoListScreenEvent.CategoryListMenuDismiss)
            },
            onSearchIconClicked = {
                todoListViewModel.processEvent(TodoListScreenEvent.SearchIconClicked)
            },
            onMenuItemClicked = {
                todoListViewModel.processEvent(TodoListScreenEvent.MenuIconClicked)
            },
            onSortIconClicked = {
                todoListViewModel.processEvent(TodoListScreenEvent.SortIconClicked)
            },
            onSortItemSelected = {
                todoListViewModel.processEvent(TodoListScreenEvent.SortOptionSelected(it))
            },
            onBackClicked = onBackClicked

        )
    )

}
