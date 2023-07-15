package com.hrudhaykanth116.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.todo.ui.screens.list.TodoListScreen
import com.hrudhaykanth116.todo.ui.screens.create.CreateOrUpdateTodoScreen
import com.hrudhaykanth116.todo.ui.models.TodoNavScreen

@Composable
fun TodoNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = TodoNavScreen.TodoListScreen.route
    ) {

        composable(route = TodoNavScreen.TodoListScreen.route) {
            TodoListScreen(
                onCreateBtnClicked = {
                    navController.navigate(
                        TodoNavScreen.CreateOrUpdateTodoScreen.route
                    )
                },
                onItemClicked = {
                    // navController.navigate(
                    //     "todo_details/${it.id}"
                    // )
                }
            )
        }

        composable(
            route = TodoNavScreen.CreateOrUpdateTodoScreen.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("id")

            CreateOrUpdateTodoScreen(
                isInEditMode = true,
                noteId = noteId,
                onCreated = {
                    navController.popBackStack(
                        route = TodoNavScreen.TodoListScreen.route,
                        inclusive = false
                    )
                }
            )
        }


    }
}