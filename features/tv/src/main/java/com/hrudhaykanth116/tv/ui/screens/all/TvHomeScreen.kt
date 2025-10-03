package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppScreen

@Composable
fun TvHomeScreen(
    viewModel: TvHomeViewModel = hiltViewModel(),
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