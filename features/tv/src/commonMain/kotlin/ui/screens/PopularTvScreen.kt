package com.hrudhaykanth116.tv.ui.screens

import androidx.compose.runtime.Composable

@Composable
expect fun PopularTvScreen(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
)
