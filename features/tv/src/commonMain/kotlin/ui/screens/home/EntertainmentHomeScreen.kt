package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.ui.components.AppScreen
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenEvent
import org.koin.compose.viewmodel.koinViewModel

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
        onTvListItemEditClicked = {
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