package com.hrudhaykanth116.composeapp.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
actual fun JournalNavigation() {
    CenteredColumn(modifier = Modifier.fillMaxSize()) {
        Text("Journal coming soon to iOS")
    }
}

@Composable
actual fun GamesNavigation() {
    CenteredColumn(modifier = Modifier.fillMaxSize()) {
        Text("Games coming soon to iOS")
    }
}

@Composable
actual fun AuthNavigation(
    navController: NavHostController,
    onLoggedIn: () -> Unit
) {
    CenteredColumn(modifier = Modifier.fillMaxSize()) {
        Text("Auth coming soon to iOS")
    }
}
