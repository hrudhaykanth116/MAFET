package com.hrudhaykanth116.mafet.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.mafet.auth.ui.screens.login.LoginScreen
import com.hrudhaykanth116.mafet.auth.ui.screens.signup.SignUpScreen
import com.hrudhaykanth116.mafet.auth.ui.screens.signup.SignUpViewModel
import com.hrudhaykanth116.mafet.home.HomeNavigation
import com.hrudhaykanth116.mafet.home.MainNavScreen
import com.hrudhaykanth116.mafet.auth.ui.data_models.Screen as AuthScreens

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = AuthScreens.LoginScreen.route
    ) {

        composable(route = AuthScreens.LoginScreen.route) {
            LoginScreen(
                onLoggedIn = {

                    navController.navigate(
                        MainNavScreen.HomeScreen.withArgs(
                            it
                        )
                    ) {
                        popUpTo(0)
                    }
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
            SignUpScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = MainNavScreen.HomeScreen.route + "/{name}",
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