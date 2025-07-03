package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.journal.JournalScreen
import com.hrudhaykanth116.mafet.account.navigation.AccountNavigation
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationUIState
import com.hrudhaykanth116.mafet.home.models.HomeRoute
import com.hrudhaykanth116.media.ui.screens.MediaScreen
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import com.hrudhaykanth116.tv.ui.TvNavigation
import com.hrudhaykanth116.tv.ui.screens.home.TvHomeScreen
import com.hrudhaykanth116.weather.ui.screens.home.WeatherNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    navController: NavHostController,
) {

    Scaffold(
        bottomBar = {

            var bottomUIState by remember {
                mutableStateOf(HomeBottomNavigationUIState())
            }

            val currentDestination = navController.currentBackStackEntryAsState().value?.destination

            val navItemList = bottomUIState.list.map{
                it.copy(
                    isSelected = currentDestination.isTopLevelDestinationInHierarchy(it.homeBottomNavigationItem)
                )
            }

            // TODO: P6 Double recomposition happens probably two states are dependent on each other here. fix.
            bottomUIState = bottomUIState.copy(
                list = navItemList
            )

            HomeBottomNavigation(
                uiState = bottomUIState,
                onNavItemSelected = { selectedItem ->
                    navController.navigate(selectedItem.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselec   ting a previously selected item
                        restoreState = true
                    }
                }
            )

        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController, startDestination = HomeRoute.Todo.route) {

                HomeRoute.getRoutes().forEach { homeRoute: HomeRoute ->
                    when (homeRoute) {
                        HomeRoute.Account -> {
                            composable(HomeRoute.Account.route) {
                                AccountNavigation()
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
                                TvNavigation()
                            }
                        }

                        HomeRoute.Journal -> {
                            composable(HomeRoute.Journal.route) {
                                JournalScreen()
                            }
                        }

                        HomeRoute.Todo -> {
                            composable(HomeRoute.Todo.route) {
                                TodoNavigation(
                                    onBackClicked = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        HomeRoute.Translate -> {
                            composable(HomeRoute.Translate.route) {
                                CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Translate feature coming up")
                                }
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
                    }
                }
            }
        }
    }

}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: HomeBottomNavigationItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

