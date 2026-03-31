package com.hrudhaykanth116.composeapp.home.models

data class HomeBottomNavigationUIState(

    val list: List<NavigationItemUIState> = listOf(
        NavigationItemUIState(HomeBottomNavigationItem.TODO),
        NavigationItemUIState(HomeBottomNavigationItem.WEATHER),
        NavigationItemUIState(HomeBottomNavigationItem.DASHBOARD),
        NavigationItemUIState(HomeBottomNavigationItem.JOURNAL),
        NavigationItemUIState(HomeBottomNavigationItem.ENTERTAINMENT),
    ),
)

data class NavigationItemUIState(
    val homeBottomNavigationItem: HomeBottomNavigationItem,
    val isSelected: Boolean = false,
)
