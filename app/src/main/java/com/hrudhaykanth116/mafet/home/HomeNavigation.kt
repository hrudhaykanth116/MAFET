package com.hrudhaykanth116.mafet.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.mafet.auth.data.local.shared_preferences.AuthPreffs
import com.hrudhaykanth116.mafet.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.mafet.todo.navigation.TodoNavigation
import com.hrudhaykanth116.mafet.home.Screen as HomeScreens

@Composable
fun HomeNavigation(
    name: String?
) {

    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeScreens.HomeScreen.route) {
        composable(HomeScreens.HomeScreen.route) {
            HomeScreen(
                name = name,
                onTodoClicked = {
                    navController.navigate(
                        HomeScreens.Todo.route
                    )
                },
                onLogoutClicked = {

                    AuthPreffs.isLoggedIn = false

                    navController.navigate(
                        HomeScreens.Auth.route
                    )
                }
            )
        }

        composable(HomeScreens.Todo.route) {
            TodoNavigation()
        }

        composable(HomeScreens.Auth.route){
            AuthNavigation()
        }
    }

}