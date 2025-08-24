package com.hrudhaykanth116.tv.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrudhaykanth116.tv.ui.screens.PopularTvScreen
import com.hrudhaykanth116.tv.ui.screens.all.TvHomeScreen
import com.hrudhaykanth116.tv.ui.screens.details.TvDetailsScreen
import com.hrudhaykanth116.tv.ui.screens.home.EntertainmentHomeScreen
import com.hrudhaykanth116.tv.ui.screens.search.SearchTvScreen

@Composable
fun EntertainmentNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "entertainment_home"
    ) {

        composable(
            route = "entertainment_home",
        ) { backStackEntry ->
            EntertainmentHomeScreen(
                onNavigateToSearchScreen = {
                    navController.navigate("tv_home")
                }
            )
        }

        composable(
            route = "tv_home",
        ) { backStackEntry ->
            TvHomeScreen(
                onNavigateToSearchScreen = {
                    navController.navigate("tv_search")
                },
                onItemClick = { id ->
                    navController.navigate("tv_details/$id")
                }
            )
        }

        composable(
            route = "tv_search"
        ) {
            SearchTvScreen()
        }

        composable(
            route = "tv_popular",
        ) { backStackEntry ->
            PopularTvScreen(
                onNavigateToSearchScreen = {
                    navController.navigate("tv_search")
                },
                onNavigateToDetailsScreen = { id ->
                    navController.navigate("tv_details/$id")
                }
            )
        }

        composable(
            route = "tv_details/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )) {
            TvDetailsScreen()
        }

    }

}