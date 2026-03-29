package com.hrudhaykanth116.composeapp.home.models

data class HomeBottomNavigationUIState(

    val list: List<NavigationItemUIState> = listOf(
        NavigationItemUIState(HomeBottomNavigationItem.TODO),
        // NavigationItemUIState(HomeBottomNavigationItem.JOURNAL),
        NavigationItemUIState(HomeBottomNavigationItem.WEATHER),
        // NavigationItemUIState(HomeBottomNavigationItem.MEDIA),
        NavigationItemUIState(HomeBottomNavigationItem.ENTERTAINMENT),
    ),
)

data class NavigationItemUIState(
    val homeBottomNavigationItem: HomeBottomNavigationItem,
    val isSelected: Boolean = false,
)
