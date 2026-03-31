package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private const val TAG = "CreateTodoListScreen"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreateOrUpdateTodoScreen(
    noteId: String? = null,
    viewModel: CreateOrUpdateTodoListViewModel = koinViewModel { parametersOf(noteId) },
    isInEditMode: Boolean = true,
    onCreated: () -> Unit,
    onBackClicked: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    sharedElementKey: String? = null,
) {
    Logger.d(TAG, "CreateTodoListScreen: ")

    AppScreen(
        viewModel = viewModel,
    ) { contentState: CreateOrUpdateTodoUIState? ->

        // No content state, nothing to display. May be a progress bar will be shown in [AppUIState]
        contentState ?: return@AppScreen

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

        val onCategoryFieldClicked = remember<() -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.OnCategoryFieldClicked) }
        }

        val onCategoryDismissRequest = remember<() -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.OnCategoryDismissRequest) }
        }

        val onCategorySelected = remember<(TaskCategory) -> Unit> {
            { viewModel.processEvent(CreateTodoEvent.CategorySelected(it)) }
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
                onTargetFieldClicked = onTargetFieldClicked,
                onCategoryFieldClicked = onCategoryFieldClicked,
                onCategoryDismissRequest = onCategoryDismissRequest,
                onCategorySelected = onCategorySelected,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedElementKey = sharedElementKey
            )
        }
    }
}