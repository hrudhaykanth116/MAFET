package com.hrudhaykanth116.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.mafet.todo.TodoListScreen
import com.hrudhaykanth116.todo.ui.screens.CreateTodoListScreen
import com.hrudhaykanth116.todo.ui.models.TodoNavScreen

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = TodoNavScreen.TodoListScreen.route) {
        composable(route = TodoNavScreen.TodoListScreen.route) {
            TodoListScreen()
        }
        composable(
            route = TodoNavScreen.CreateTodoScreen.route,
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "hrudhay"
                    nullable = true
                }
            )
        ){ backStackEntry: NavBackStackEntry ->
            CreateTodoListScreen()
        }

    }
}