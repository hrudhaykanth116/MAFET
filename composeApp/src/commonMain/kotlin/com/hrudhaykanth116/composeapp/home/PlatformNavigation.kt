package com.hrudhaykanth116.composeapp.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun JournalNavigation()

@Composable
expect fun GamesNavigation()

@Composable
expect fun AuthNavigation(
    navController: NavHostController,
    onLoggedIn: () -> Unit
)
