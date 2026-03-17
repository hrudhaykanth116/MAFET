package com.hrudhaykanth116.media.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.models.UIState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaScreen(
    viewModel: MediaViewModel = koinViewModel(),
) {
    val state: UIState<MediaScreenUIState> by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val onEvent: (MediaScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    // AppUIState(
    //     state,
    //     onUserMessageShown = {}
    // ) {
    //
    //     MediaScreenUI(
    //         state = it ?: MediaScreenUIState(),
    //         processEvent = onEvent,
    //         modifier = Modifier
    //     )
    //
    // }

}