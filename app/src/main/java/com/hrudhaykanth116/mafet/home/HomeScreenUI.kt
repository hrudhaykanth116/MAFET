package com.hrudhaykanth116.mafet.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.games.GameScreen
import com.hrudhaykanth116.journal.JournalScreen
import com.hrudhaykanth116.mafet.account.navigation.AccountNavigation
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationUIState
import com.hrudhaykanth116.mafet.home.models.HomeRoute
import com.hrudhaykanth116.media.ui.screens.MediaScreen
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import com.hrudhaykanth116.tv.ui.EntertainmentNavigation
import com.hrudhaykanth116.weather.ui.screens.home.WeatherNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    navController: NavHostController,
    bottomUIState: HomeBottomNavigationUIState,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit,
) {

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                uiState = bottomUIState,
                onNavItemSelected = onNavItemSelected,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier
    ) { padding ->


        Box(modifier = Modifier.padding(padding)) {
            NavHost(navController, startDestination = HomeRoute.Todo.route) {

                HomeRoute.getRoutes().forEach { homeRoute: HomeRoute ->
                    when (homeRoute) {

                        HomeRoute.Todo -> {
                            composable(HomeRoute.Todo.route) {
                                TodoNavigation(
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        HomeRoute.Weather -> {
                            composable(HomeRoute.Weather.route) {
                                // If use compose navigation
                                WeatherNavigation()
                            }
                        }

                        HomeRoute.Media -> {
                            composable(homeRoute.route) {
                                MediaScreen()
                            }
                        }

                        HomeRoute.Translate -> {
                            composable(HomeRoute.Translate.route) {
                                CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Translate feature coming up")
                                }
                            }
                        }

                        HomeRoute.Journal -> {
                            composable(HomeRoute.Journal.route) {
                                JournalScreen()
                            }
                        }

                        HomeRoute.Dictionary -> {
                            composable(HomeRoute.Dictionary.route) {
                                CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Dictionary feature coming up")
                                }
                            }
                        }

                        HomeRoute.Entertainment -> {
                            composable(HomeRoute.Entertainment.route) {
                                // EntertainmentNavigation()
                                GameScreen()
                            }
                        }

                        HomeRoute.Account -> {
                            composable(HomeRoute.Account.route) {
                                AccountNavigation()
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun HomeScreenUIPreview() {

    val navController = rememberNavController()

    HomeScreenUI(
        navController,
        HomeBottomNavigationUIState(

        ),
        onNavItemSelected = {}

    )
}
