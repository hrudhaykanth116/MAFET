package com.hrudhaykanth116.journal.navigation

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
import com.hrudhaykanth116.journal.navigation.models.JournalEntryRoute
import com.hrudhaykanth116.journal.navigation.models.JournalList
import com.hrudhaykanth116.journal.ui.screens.entry.JournalEntryScreen
import com.hrudhaykanth116.journal.ui.screens.list.JournalListScreen

@Composable
fun JournalNavigation(
    onBackClicked: () -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = JournalList,
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
        composable<JournalList> {
            JournalListScreen(
                onNavigateToEntry = { entryId ->
                    navController.navigate(JournalEntryRoute(entryId))
                },
                onBackClicked = onBackClicked
            )
        }

        composable<JournalEntryRoute> { backStackEntry ->
            val route: JournalEntryRoute = backStackEntry.toRoute()

            JournalEntryScreen(
                entryId = route.id,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}
