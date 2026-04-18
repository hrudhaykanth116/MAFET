package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.runtime.Composable

@Composable
expect fun TvShowsScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onBackClicked: () -> Unit = {},
)
