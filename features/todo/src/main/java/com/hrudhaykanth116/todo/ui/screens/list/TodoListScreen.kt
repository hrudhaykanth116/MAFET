package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    // Property delegation helps us in remembering the value of state directly into the field instead
    // Remember savable saves the values over configuration changes.
    var test by rememberSaveable() {
        mutableStateOf(true)
    }

    val uiState: State<TodoListUIState> =
        todoListViewModel.uiStateFlow.collectAsStateWithLifecycle()

    // val list by todoViewModel.todoList.observeAsState(listOf())

    TodoListScreenUI(
        modifier = Modifier,
        uiState = uiState.value,
        onRemoveTask = { id: String ->
            todoListViewModel.processEvent(TodoListScreenEvent.RemoveTasks(listOf(id)))
        },
        onItemClicked = onItemClicked,
        onTodoTitleChanged = {
            todoListViewModel.processEvent(TodoListScreenEvent.TodoTaskTitleChanged(it))
        },
        onCreateBtnClicked = {
            val todoTitle = uiState.value.todoTitle.text
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
            onBackClicked = onBackClicked

        )
    )

}
