package com.hrudhaykanth116.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hrudhaykanth116.auth.ui.data_models.AuthRoutes
import com.hrudhaykanth116.auth.ui.screens.login.LoginScreen
import com.hrudhaykanth116.auth.ui.screens.signup.SignUpScreen
import com.hrudhaykanth116.auth.ui.screens.signup.SignUpViewModel

@Composable
fun AuthNavigation(
    navController: NavHostController,
    onLoggedIn: () -> Unit = {},
) {
    NavHost(
        navController,
        startDestination = AuthRoutes.LoginRoute.route
    ) {

        composable(route = AuthRoutes.LoginRoute.route) {
            LoginScreen(
                onLoggedIn = onLoggedIn,
                navigateToSignUpScreen = {
                    navController.navigate(
                        AuthRoutes.SignUpRoute.withArgs(

                        )
                    )
                }
            )
        }

        composable(route = AuthRoutes.SignUpRoute.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(
                viewModel = viewModel
            )
        }

    }
}