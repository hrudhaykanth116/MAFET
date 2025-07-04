package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState

@Composable
fun TvHomeScreen(
    onNavigateToSearchScreen: () -> Unit,
    tvHomeScreenViewModel: TvHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<TvHomeScreenUIState> =
        tvHomeScreenViewModel.collectAsState()

    val userMessage = state.value.userMessage
    if (userMessage != null) {
        ToastHelper.show(LocalContext.current, userMessage)
    }

    val tvHomeScreenCallbacks = TvHomeScreenCallbacks(
        onAddNewClicked = onNavigateToSearchScreen,
        onUpdateTvCloseRequest = {
            tvHomeScreenViewModel.processEvent(TvHomeScreenEvent.CloseUpdateTv)
        },
        onTvListItemClicked = {
            tvHomeScreenViewModel.processEvent(TvHomeScreenEvent.MyTvListItemClicked(it))
        },
        onTvListItemDismissed = {
            tvHomeScreenViewModel.processEvent(TvHomeScreenEvent.Delete(it))
        }
    )

    TvHomeScreenUI(
        state = state.value,
        tvHomeScreenCallbacks = tvHomeScreenCallbacks,
    )


}