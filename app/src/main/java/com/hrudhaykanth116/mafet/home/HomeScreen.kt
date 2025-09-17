package com.hrudhaykanth116.mafet.home

import android.net.http.SslCertificate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationUIState

@Composable
fun HomeScreen(

) {

    val navController: NavHostController = rememberNavController()

    var bottomUIState by remember {
        mutableStateOf(HomeBottomNavigationUIState())
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val navItemList = bottomUIState.list.map{
        it.copy(
            isSelected = currentDestination.isTopLevelDestinationInHierarchy(it.homeBottomNavigationItem)
        )
    }

    bottomUIState = bottomUIState.copy(
        list = navItemList
    )

    HomeScreenUI(
        navController,
        bottomUIState,
        onNavItemSelected = { selectedItem: HomeBottomNavigationItem ->
            navController.navigate(selectedItem.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // re selecting the same item
                launchSingleTop = true
                // Restore state when re select   ting a previously selected item
                restoreState = true
            }
        }
    )

}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: HomeBottomNavigationItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false