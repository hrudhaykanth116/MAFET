package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenUIState

@Composable
fun EntertainmentHomeScreen(
    onNavigateToSearchScreen: () -> Unit,
    entertainmentHomeScreenViewModel: EntertainmentHomeScreenViewModel = hiltViewModel(),
) {

    val state: State<EntertainmentHomeScreenUIState> =
        entertainmentHomeScreenViewModel.collectAsState()

    val userMessage = state.value.userMessage
    if (userMessage != null) {
        ToastHelper.show(LocalContext.current, userMessage)
    }

    val entertainmentHomeScreenCallbacks = EntertainmentHomeScreenCallbacks(
        onAddNewClicked = onNavigateToSearchScreen,
        onUpdateTvCloseRequest = {
            entertainmentHomeScreenViewModel.processEvent(EntertainmentHomeScreenEvent.CloseUpdateTv)
        },
        onTvListItemClicked = {
            entertainmentHomeScreenViewModel.processEvent(EntertainmentHomeScreenEvent.MyEntertainmentListItemClicked(it))
        },
        onTvListItemDismissed = {
            entertainmentHomeScreenViewModel.processEvent(EntertainmentHomeScreenEvent.Delete(it))
        }
    )

    EntertainmentHomeScreenUI(
        state = state.value,
        entertainmentHomeScreenCallbacks = entertainmentHomeScreenCallbacks,
    )


}