package com.hrudhaykanth116.mafet.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.auth.ui.data_models.AuthRoutes
import com.hrudhaykanth116.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.core.theme.AppTheme
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.home.HomeScreen
import com.hrudhaykanth116.training.core.TrainingScreen

@Composable
fun MainActivityScreen(
    uiState: MainUiState,
    onLoggedIn: () -> Unit,
) {

    val navController = rememberNavController()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    HomeScreen(
                        // uiState.userName
                    )
                }

                MainUiState.LoggedOut -> {
                    AuthNavigation(
                        navController,
                        onLoggedIn = onLoggedIn
                    )
                }

                MainUiState.Training -> TrainingScreen()
            }

        }
    }
}