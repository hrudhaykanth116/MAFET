package com.hrudhaykanth116.composeapp.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationUIState
import com.hrudhaykanth116.composeapp.home.models.NavigationItemUIState
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun HomeScreen(
    features: List<Feature> = emptyList(),
) {

    if(features.isEmpty()){
        CenteredColumn(modifier = Modifier.fillMaxSize()) {
            Text("No features enabled. Please wait for the features to be enabled.")
        }
        return
    }

    val navController = rememberNavController()

    val bottomNavigationUIState = remember(features) {
        val featureNavigationItemUIStates = features.mapNotNull { feature ->
            HomeBottomNavigationItem.getFromKey(feature)?.let { navItem ->
                NavigationItemUIState(navItem)
            }
        }

        val finalList = if (featureNavigationItemUIStates.size <= 1) {
            featureNavigationItemUIStates
        } else {
            val mid = featureNavigationItemUIStates.size / 2
            buildList {
                addAll(featureNavigationItemUIStates.take(mid))
                add(NavigationItemUIState(HomeBottomNavigationItem.DASHBOARD))
                addAll(featureNavigationItemUIStates.drop(mid))
            }
        }

        HomeBottomNavigationUIState(
            finalList
        )
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    // not used currently
    val selectedItem = remember(currentDestination) {
        bottomNavigationUIState.list.find {
            currentDestination.isTopLevelDestinationInHierarchy(it.homeBottomNavigationItem)
        } ?: bottomNavigationUIState.list.first()
    }.homeBottomNavigationItem

    val bottomUIState = remember(bottomNavigationUIState, currentDestination) {
        bottomNavigationUIState.copy(
            list = bottomNavigationUIState.list.map {
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
