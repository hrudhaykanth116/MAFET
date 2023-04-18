package com.hrudhaykanth116.mafet.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.mafet.account.navigation.AccountNavigation
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import com.hrudhaykanth116.mafet.home.models.MainNavRoute as HomeScreens

@Composable
fun HomeNavigation(
    name: String?
) {

    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeScreens.HomeRoute.route) {
        composable(HomeScreens.HomeRoute.route) {
            HomeScreen(
                name = name,
                onTodoClicked = {
                    navController.navigate(
                        HomeScreens.Todo.route
                    )
                },
                onAccountClicked = {
                    navController.navigate(
                        HomeScreens.Account.route
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(HomeScreens.Todo.route) {
            TodoNavigation()
        }

        composable(HomeScreens.Account.route){
            AccountNavigation()
        }
    }

}