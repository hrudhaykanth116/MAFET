package com.hrudhaykanth116.composeapp.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.composeapp.home.models.FeatureConfig
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationUIState
import com.hrudhaykanth116.composeapp.home.models.NavigationItemUIState
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun HomeScreen(
    features: List<FeatureConfig> = emptyList(),
) {

    if(features.isEmpty()){
        CenteredColumn() {
            Text("No features enabled. Please wait for the features to be enabled.")
        }
        return
    }

    val navController = rememberNavController()

    val baseUIState = remember(features) {
        val enabledKeys = features
            .filter { it.enabled }
            .map { it.key }
            .toSet()

        val items = Feature.set
            .filter { it.key in enabledKeys }
            .map { feature ->
                val navItem = when (feature) {
                    Feature.TODO -> HomeBottomNavigationItem.TODO
                    Feature.JOURNAL -> HomeBottomNavigationItem.JOURNAL
                    Feature.AI -> HomeBottomNavigationItem.AI
                    Feature.WEATHER -> HomeBottomNavigationItem.WEATHER
                    Feature.WATCHLIST -> HomeBottomNavigationItem.ENTERTAINMENT
                    Feature.MEDIA -> HomeBottomNavigationItem.MEDIA
                }
                NavigationItemUIState(navItem)
            }

        HomeBottomNavigationUIState(
            list = listOf(
                NavigationItemUIState(HomeBottomNavigationItem.DASHBOARD)
            ) + items
        )
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val selectedItem = remember(currentDestination) {
        baseUIState.list.find {
            currentDestination.isTopLevelDestinationInHierarchy(it.homeBottomNavigationItem)
        } ?: baseUIState.list.first()
    }.homeBottomNavigationItem

    val bottomUIState = remember(baseUIState, currentDestination) {
        baseUIState.copy(
            list = baseUIState.list.map {
                it.copy(
                    isSelected = currentDestination.isTopLevelDestinationInHierarchy(it.homeBottomNavigationItem)
                )
            }
        )
    }

    HomeScreenUI(
        navController,
        bottomUIState,
        onNavItemSelected = { selectedItem: HomeBottomNavigationItem ->
            navController.navigate(selectedItem.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: HomeBottomNavigationItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
