package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TodoListScreen(
    todoListViewModel: TodoListViewModel = koinViewModel(),
    navigateToCreateScreen: () -> Unit,
    onItemClicked: (TodoUIModel) -> Unit,
    onBackClicked: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {

    LaunchedEffect(Unit) {
        todoListViewModel.initializeData()
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
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
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
            onSearchTextChanged = {
                todoListViewModel.processEvent(TodoListScreenEvent.Search(it))
            },
            onCloseSearch = {
                todoListViewModel.processEvent(TodoListScreenEvent.CloseSearch)
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
