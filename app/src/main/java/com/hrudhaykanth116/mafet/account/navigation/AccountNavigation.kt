package com.hrudhaykanth116.mafet.account.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.mafet.account.navigation.ui.models.AccountNavScreen
import com.hrudhaykanth116.mafet.account.navigation.ui.screen.AccountScreen
import com.hrudhaykanth116.mafet.home.MainNavScreen

@Composable
fun AccountNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AccountNavScreen.AccountScreen.route) {
        composable(route = AccountNavScreen.AccountScreen.route) {
            AccountScreen {
                navController.navigate(
                    MainNavScreen.Auth.route
                ) {
                    popUpTo(0)
                }
            }
        }
    }
}