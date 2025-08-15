package com.hrudhaykanth116.tv.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.tv.ui.screens.PopularTvScreen
import com.hrudhaykanth116.tv.ui.screens.home.TvHomeScreen
import com.hrudhaykanth116.tv.ui.screens.search.SearchTvScreen

@Composable
fun EntertainmentNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "tv_home"
    ) {

        composable(
            route = "tv_home",
        ) { backStackEntry ->
            TvHomeScreen(
                onNavigateToSearchScreen = {
                    navController.navigate("tv_popular")
                }
            )
        }

        composable(
            route = "tv_popular",
        ) { backStackEntry ->
            PopularTvScreen(
                onNavigateToSearchScreen = {
                    navController.navigate("tv_search")
                }
            )
        }

        composable(
            route = "tv_search"
        ){
            SearchTvScreen()
        }

    }

}