package com.hrudhaykanth116.media.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.models.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaScreen(
    modifier: Modifier = Modifier,
    viewModel: MediaViewModel = koinViewModel(),
) {
    val uiState: UIState<MediaScreenUIState> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val onEvent: (MediaScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    MediaScreenUI(
        modifier = modifier,
        uiState = uiState,
        state = uiState.contentState ?: MediaScreenUIState(),
        processEvent = onEvent,
    )

}