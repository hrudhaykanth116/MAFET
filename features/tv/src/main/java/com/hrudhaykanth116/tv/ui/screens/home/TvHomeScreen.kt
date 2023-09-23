package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState

@Composable
fun TvHomeScreen(
    onNavigateToSearchScreen: () -> Unit,
    tvHomeScreenViewModel: TvHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<TvHomeScreenUIState> =
        tvHomeScreenViewModel.stateFlow.collectAsStateWithLifecycle()

    val userMessage = state.value.userMessage
    if (userMessage != null) {
        ToastHelper.show(LocalContext.current, userMessage)
    }

    val tvHomeScreenCallbacks = TvHomeScreenCallbacks(
        onAddNewClicked = onNavigateToSearchScreen,
        onUpdateTvCloseRequest = {}
    )

    TvHomeScreenUI(
        state = state.value,
        tvHomeScreenCallbacks = tvHomeScreenCallbacks,
    )


}