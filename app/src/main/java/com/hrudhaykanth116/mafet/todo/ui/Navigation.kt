package com.hrudhaykanth116.mafet.todo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.mafet.todo.TodoListScreen
import com.hrudhaykanth116.mafet.todo.ui.data_models.Screen

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.TodoListScreen.route) {
        composable(route = Screen.TodoListScreen.route) {
            TodoListScreen()
        }
        composable(
            route = Screen.CreateTodoScreen.route,
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