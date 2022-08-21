package com.hrudhaykanth116.mafet.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.mafet.auth.ui.data_models.Screen as AuthScreens
import com.hrudhaykanth116.mafet.auth.ui.screens.LoginScreen
import com.hrudhaykanth116.mafet.main.HomeScreen
import com.hrudhaykanth116.mafet.main.Screen as MainScreens

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AuthScreens.LoginScreen.route) {

        composable(route = AuthScreens.LoginScreen.route) {
            LoginScreen(navigateToHomeScreen = {
                navController.navigate(
                    MainScreens.HomeScreen.withArgs(
                        it
                    )
                )
            })
        }

        composable(
            route = MainScreens.HomeScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Guest"
                    nullable = true
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            HomeScreen(name = backStackEntry.arguments?.getString("name"))
        }

    }
}