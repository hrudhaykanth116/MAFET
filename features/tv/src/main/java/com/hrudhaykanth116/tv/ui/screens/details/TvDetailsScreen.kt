package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.ui.components.AppUIState

@Composable
fun TvDetailsScreen(
    viewModel: TvDetailsViewModel = hiltViewModel(),
) {

    val onEvent: (TvDetailsScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    AppUIState(viewModel) { state ->
        state?.let {
            TvDetailsScreenUI(
                state = it,
                processEvent = onEvent,
                modifier = Modifier,
            )
        }

    }

}