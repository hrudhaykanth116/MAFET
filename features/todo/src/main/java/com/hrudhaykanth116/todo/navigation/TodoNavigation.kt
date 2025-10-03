package com.hrudhaykanth116.todo.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
        startDestination = TodoNavScreen.TodoListScreen.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(320, easing = FastOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth / 3 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth / 3 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(320, easing = FastOutSlowInEasing)
            )
        }
    ) {


        // hrudhay_check_list: Use optional params and use utils function
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
                    // hrudhay_check_list: ISSUES jst popBackStack()
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