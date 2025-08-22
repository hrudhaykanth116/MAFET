package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.UIState

@Composable
fun TvHomeScreen(
    viewModel: TvHomeViewModel = hiltViewModel(),
    onNavigateToSearchScreen: () -> Unit,
) {
    val state by viewModel.uiStateTemp.collectAsStateWithLifecycle()

    val onEvent: (TvHomeScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    TvHomeScreenUI(
        uiState = state,
        processEvent = onEvent,
        modifier = Modifier
    )

    // AppUIState(viewModel) { state ->
    //     TvHomeScreenUI(
    //         state = state!!,
    //         processEvent = onEvent,
    //         modifier = Modifier
    //     )
    //
    // }

}