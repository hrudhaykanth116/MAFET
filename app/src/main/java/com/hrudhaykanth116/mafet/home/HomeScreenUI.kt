package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.account.navigation.AccountNavigation
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeRoute
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import com.hrudhaykanth116.weather.ui.screens.home.WeatherNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    navController: NavHostController,
) {

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                navItems = HomeBottomNavigationItem.values(),
                onNavItemSelected = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController, startDestination = HomeRoute.Todo.route) {


                HomeRoute.getRoutes().forEach { homeRoute ->
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
                                CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Entertainment feature coming up")
                                }
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
                    }
                }
            }
        }
    }

}