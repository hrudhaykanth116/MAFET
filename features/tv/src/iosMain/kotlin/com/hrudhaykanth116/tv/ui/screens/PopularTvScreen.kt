package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun PopularTvScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onBackClicked: () -> Unit,
) {
    val viewModel: PopularTvViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    PopularTvScreenUI(
        uiState = uiState,
        categoryName = viewModel.category.displayName,
        onNavigateToSearchScreen = onNavigateToSearchScreen,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        onBackClicked = onBackClicked,
        onRetry = { viewModel.retry() },
        modifier = Modifier.fillMaxSize()
    )
}
