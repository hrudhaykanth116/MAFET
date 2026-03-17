package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import com.hrudhaykanth116.core.common.utils.ui.ToastHelper
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun EntertainmentHomeScreen(
    onNavigateToSearchScreen: () -> Unit,
    entertainmentHomeScreenViewModel: EntertainmentHomeScreenViewModel = koinViewModel(),
) {


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

    AppScreen(
        viewModel = entertainmentHomeScreenViewModel
    ) { state ->
        EntertainmentHomeScreenUI(
            state = state,
            entertainmentHomeScreenCallbacks = entertainmentHomeScreenCallbacks,
        )
    }


}