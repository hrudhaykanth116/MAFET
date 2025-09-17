package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent

private const val TAG = "CreateTodoListScreen"

@Composable
fun CreateOrUpdateTodoScreen(
    viewModel: CreateOrUpdateTodoListViewModel = hiltViewModel(),
    noteId: String? = null,
    isInEditMode: Boolean = true,
    onCreated: () -> Unit,
    onBackClicked: () -> Unit = {},
) {
    Logger.d(TAG, "CreateTodoListScreen: ")

    val state: UIState<CreateOrUpdateTodoUIState> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    AppUIState(
        viewModel = viewModel,
    ) { contentState: CreateOrUpdateTodoUIState? ->

        // No content state, nothing to display. May be a progress bar will be shown in [AppUIState]
        contentState ?: return@AppUIState

        // Remembered lambdas prevent recomposition as lambdas are considered unstable.
        val onTitleChanged = remember<(TextFieldValue) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.TitleChanged(it)) }
        }

        val onDescriptionChanged = remember<(TextFieldValue) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.DescriptionChanged(it)) }
        }

        val onCreateBtnClicked = remember {
            { viewModel.processEvent(CreateTodoEvent.Submit) }
        }

        val onCategoryChanged = remember<(TextFieldValue) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.CategoryChanged(it)) }
        }

        val onPriorityChanged = remember<(Int) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.PriorityChanged(it)) }
        }

        val onTargetTimeDateTimePickerCloseRequest = remember<() -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.OnTargetTimeDateTimePickerCloseRequest) }
        }

        val onTargetTimeChanged = remember<(Long) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.OnTargetTimeChanged(it)) }
        }

        val onTargetFieldClicked = remember<() -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.OnTargetFieldClicked) }
        }

        if (contentState.isSubmitted) {
            onCreated()
        } else {
            CreateOrUpdateTodoScreenUI(
                state = contentState,
                onTitleChanged = onTitleChanged,
                onDescriptionChanged = onDescriptionChanged,
                onCreateBtnClicked = onCreateBtnClicked,
                onCategoryChanged = onCategoryChanged,
                onBackClicked = onBackClicked,
                onPriorityChanged = onPriorityChanged,
                onTargetTimeDateTimePickerCloseRequest = onTargetTimeDateTimePickerCloseRequest,
                onTargetTimeChanged = onTargetTimeChanged,
                onTargetFieldClicked = onTargetFieldClicked
            )
        }
    }
}