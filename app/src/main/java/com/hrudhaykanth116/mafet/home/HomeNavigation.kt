package com.hrudhaykanth116.mafet.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.mafet.account.navigation.AccountNavigation
import com.hrudhaykanth116.mafet.home.models.HomeScreenNavigationHandler
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import com.hrudhaykanth116.weather.ui.screens.home.WeatherNavigation
import com.hrudhaykanth116.mafet.home.models.MainNavRoute as HomeScreens

@Composable
fun HomeNavigation(
    name: String?,
) {

    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeScreens.HomeRoute.route) {

        composable(HomeScreens.HomeRoute.route) {
            HomeScreenContainer(name, navController)
        }

        composable(HomeScreens.Todo.route) {
            TodoNavigation(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(HomeScreens.Account.route) {
            AccountNavigation()
        }

        composable(HomeScreens.Weather.route) {
            // If use compose navigation
            WeatherNavigation()
        }
    }

}

// TODO: Just extracted, look for appropriate refactor
@Composable
private fun HomeScreenContainer(name: String?, navController: NavHostController) {

    val context = LocalContext.current

    val homeScreenNavigationHandler = HomeScreenNavigationHandler(context, navController)

    HomeScreen(
        name = name,
        homeScreenNavigationHandler = homeScreenNavigationHandler
    )

}