package com.hrudhaykanth116.composeapp.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hrudhaykanth116.games.GameScreenStevdza

@Composable
actual fun GamesNavigation() {
    GameScreenStevdza()
}

@Composable
actual fun AuthNavigation(
    navController: NavHostController,
    onLoggedIn: () -> Unit
) {
    com.hrudhaykanth116.auth.ui.navigation.AuthNavigation(navController, onLoggedIn)
}
