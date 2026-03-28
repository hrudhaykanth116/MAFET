package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TvHomeScreen(
    viewModel: TvHomeViewModel = koinViewModel(),
    onNavigateToSearchScreen: () -> Unit,
    onItemClick: (Int) -> Unit,
    onBackClicked: () -> Unit,
) {

    val onEvent: (TvHomeScreenEvent) -> Unit = {
        viewModel.processEvent(it)
    }

    AppScreen(
        viewModel = viewModel
    ){ state ->
        TvHomeScreenUI(
            uiState = state,
            processEvent = onEvent,
            onNavigateToSearch = onNavigateToSearchScreen,
            onItemClick = onItemClick,
            modifier = Modifier,
            onBackClick = onBackClicked
        )
    }



}