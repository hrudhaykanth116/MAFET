package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.UIState

@Composable
fun TvDetailsScreen(
    viewModel: TvDetailsViewModel = hiltViewModel(),
) {
    val state: UIState<TvDetailsScreenUIState> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val onEvent: (TvDetailsScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    AppUIState(viewModel) {
        TvDetailsScreenUI(
            state = state.contentState!!,
            processEvent = onEvent,
            modifier = Modifier,
        )

    }

}