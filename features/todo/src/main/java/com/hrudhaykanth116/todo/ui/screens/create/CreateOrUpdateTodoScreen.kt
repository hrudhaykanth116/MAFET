package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent

private const val TAG = "CreateTodoListScreen"

@Composable
fun CreateOrUpdateTodoScreen(
    viewModel: CreateOrUpdateTodoListViewModel = hiltViewModel(),
    noteId: String? = null,
    isInEditMode: Boolean = true,
    onCreated: () -> Unit,
) {
    Logger.d(TAG, "CreateTodoListScreen: ")

    val state: UIState<CreateOrUpdateTodoUIState> by viewModel.stateFlow.collectAsStateWithLifecycle()

    TodoScreenUI(
        state,
        onCreated,
        onEvent = viewModel::processEvent
    )
}

@Composable
private fun TodoScreenUI(
    uiState: UIState<CreateOrUpdateTodoUIState>,
    onCreated: () -> Unit,
    onEvent: (CreateTodoEvent) -> Unit,
) {
    AppUIState(state = uiState) { contentState: CreateOrUpdateTodoUIState ->

        if (contentState.isSubmitted) {
            onCreated()
        } else {
            CreateTodoUI(
                state = contentState,
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