package com.hrudhaykanth116.composeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.composeapp.home.AuthNavigation
import com.hrudhaykanth116.composeapp.home.HomeScreen
import com.hrudhaykanth116.composeapp.models.MainUiState
import com.hrudhaykanth116.composeapp.ui.components.AppGateDialog
import com.hrudhaykanth116.composeapp.viewmodels.MainViewModel
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App(
    uiState: MainUiState,
    viewModel: MainViewModel = koinViewModel(),
) {

    val navController = rememberNavController()

    AppUI(uiState, navController, onLoggIn = {
        viewModel.onLoggedIn()
    })

}

@Composable
fun AppUI(uiState: MainUiState, navController: NavHostController, onLoggIn: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        when (uiState) {
            MainUiState.Loading -> {
                // This state is handled using splash screen.
                CenteredColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Green)
                ) {
                    CircularProgressIndicator()
                }
            }

            is MainUiState.LoggedIn -> {
                // TODO: Consider showing error screen if features is empty with server message.
                HomeScreen(uiState.features)
            }

            MainUiState.LoggedOut -> {
                AuthNavigation(
                    navController,
                    onLoggedIn = onLoggIn
                )
            }

            is MainUiState.Blocked -> AppGateDialog(config = uiState.config)
        }

    }
}
