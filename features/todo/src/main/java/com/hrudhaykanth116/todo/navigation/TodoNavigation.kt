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
fun TodoNavigation(
    onBackClicked: () -> Unit = {},
) {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = TodoNavScreen.TodoListScreen.route
    ) {

        // TODO: Use optional params and use utils function
        composable(route = TodoNavScreen.TodoListScreen.route) {
            TodoListScreen(
                navigateToCreateScreen = {
                    navController.navigate(
                        "create_todo_screen"
                    )
                },
                onItemClicked = {
                    navController.navigate(
                        "create_todo_screen?id=${it.id}"
                    )
                },
                onBackClicked = onBackClicked
            )
        }

        composable(
            route = "create_todo_screen?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("id")

            CreateOrUpdateTodoScreen(
                isInEditMode = true,
                noteId = noteId,
                onCreated = {
                    // TODO: ISSUES jst popBackStack()
                    navController.popBackStack(
                        route = TodoNavScreen.TodoListScreen.route,
                        inclusive = false
                    )
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }


    }
}