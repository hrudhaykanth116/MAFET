package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.models.UIState
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
) {
    Logger.d(TAG, "TodoListScreen: ")

    // Property delegation helps us in remembering the value of state directly into the field instead
    // Remember savable saves the values over configuration changes.
    var test by rememberSaveable() {
        mutableStateOf(true)
    }

    val uiState: State<UIState<TodoListUIState>> =
        todoListViewModel.uiStateFlow.collectAsStateWithLifecycle()

    // val list by todoViewModel.todoList.observeAsState(listOf())

    TodoListScreenUI(
        modifier = Modifier,
        uiState = uiState.value,
        onRemoveTask = { toDoTask ->
            todoListViewModel.processEvent(TodoListScreenEvent.RemoveTask(toDoTask.data.id!!))
        },
        onItemClicked = onItemClicked,
        onTodoTitleChanged = {
            todoListViewModel.processEvent(TodoListScreenEvent.TodoTaskTitleChanged(it))
        },
        onCreateBtnClicked = {
            val todoTitle = uiState.value.contentState?.todoTitle?.text
            if(todoTitle.isNullOrEmpty()){
                navigateToCreateScreen()
            }else{
                todoListViewModel.processEvent(TodoListScreenEvent.CreateTodoTask(todoTitle))
            }
        },
    )

}
