package com.hrudhaykanth116.composeapp.home.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hrudhaykanth116.composeapp.home.dashboard.ui.DashboardScreenUI
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    DashboardScreenUI(
        uiState = uiState,
        onEvent = viewModel::processEvent
    )
}
