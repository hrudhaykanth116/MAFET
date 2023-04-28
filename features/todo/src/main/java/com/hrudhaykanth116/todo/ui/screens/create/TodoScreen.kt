package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.todo.domain.model.create.TodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent

private const val TAG = "CreateTodoListScreen"

@Composable
fun TodoScreen(
    viewModel: CreateTodoListViewModel = hiltViewModel(),
    noteId: String? = null,
    isInEditMode: Boolean = true,
    onCreated: () -> Unit,
) {
    Logger.d(TAG, "CreateTodoListScreen: ")

    val state: UIState<TodoUIState> by viewModel.stateFlow.collectAsStateWithLifecycle()

    TodoScreenUI(
        state,
        onCreated,
        onEvent = viewModel::processEvent
    )
}

@Composable
private fun TodoScreenUI(
    state: UIState<TodoUIState>,
    onCreated: () -> Unit,
    onEvent: (CreateTodoEvent) -> Unit,
) {
    AppUIState(state = state) { todoUIState: TodoUIState ->

        if (todoUIState.isSubmitted) {
            onCreated()
        } else {
            CreateTodoUI(
                state = todoUIState,
                onTitleChanged = {
                    onEvent(CreateTodoEvent.TitleChanged(it))
                },
                onDescriptionChanged = {
                    onEvent(CreateTodoEvent.DescriptionChanged(it))
                },
                onCreateBtnClicked = {
                    onEvent(CreateTodoEvent.Create)
                }
            )
        }
    }
}