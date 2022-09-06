package com.hrudhaykanth116.mafet.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.mafet.auth.data.local.shared_preferences.AuthPreffs
import com.hrudhaykanth116.mafet.auth.ui.SignUpViewModel
import com.hrudhaykanth116.mafet.auth.ui.data_models.Screen as AuthScreens
import com.hrudhaykanth116.mafet.auth.ui.screens.LoginScreen
import com.hrudhaykanth116.mafet.auth.ui.screens.SignUpScreen
import com.hrudhaykanth116.mafet.home.HomeNavigation
import com.hrudhaykanth116.mafet.home.Screen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AuthScreens.LoginScreen.route) {

        composable(route = AuthScreens.LoginScreen.route) {
            LoginScreen(
                navigateToHomeScreen = {

                    AuthPreffs.isLoggedIn = true

                    navController.navigate(
                        Screen.HomeScreen.withArgs(
                            it
                        )
                    )
                },
                navigateToSignUpScreen = {
                    navController.navigate(
                        AuthScreens.SignUpScreen.withArgs(

                        )
                    )
                }
            )
        }

        composable(route = AuthScreens.SignUpScreen.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(viewModel)
        }

        composable(
            route = Screen.HomeScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Guest"
                    nullable = true
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            HomeNavigation(name = backStackEntry.arguments?.getString("name"))
        }

    }
}