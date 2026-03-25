package com.hrudhaykanth116.todo.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hrudhaykanth116.todo.navigation.models.CreateTodo
import com.hrudhaykanth116.todo.navigation.models.TodoList
import kotlinx.serialization.Serializable
import com.hrudhaykanth116.todo.ui.screens.list.TodoListScreen
import com.hrudhaykanth116.todo.ui.screens.create.CreateOrUpdateTodoScreen

@Composable
fun TodoNavigation(
    onBackClicked: () -> Unit = {},
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TodoList,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 4 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(tween(400))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 4 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeOut(tween(400))
        }
    ) {

        composable<TodoList> {
            TodoListScreen(
                navigateToCreateScreen = {
                    navController.navigate(CreateTodo())
                },
                onItemClicked = {
                    navController.navigate(CreateTodo(it.id))
                },
                onBackClicked = onBackClicked
            )
        }

        composable<CreateTodo> { backStackEntry ->

            val route: CreateTodo = backStackEntry.toRoute()

            CreateOrUpdateTodoScreen(
                isInEditMode = route.id != null,
                noteId = route.id,
                onCreated = {
                    navController.popBackStack()
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}