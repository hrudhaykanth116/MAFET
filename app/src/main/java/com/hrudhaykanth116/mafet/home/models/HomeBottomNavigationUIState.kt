package com.hrudhaykanth116.mafet.home.models

data class HomeBottomNavigationUIState(

    // TODO: P7 Combine as one state. navigationItem, isSelected.
    val list: List<NavigationItemUIState> = listOf(
        NavigationItemUIState(HomeBottomNavigationItem.TODO),
        NavigationItemUIState(HomeBottomNavigationItem.WEATHER),
        NavigationItemUIState(HomeBottomNavigationItem.ENTERTAINMENT, true),
        NavigationItemUIState(HomeBottomNavigationItem.ACCOUNT),
    ),
)

data class NavigationItemUIState(
    val homeBottomNavigationItem: HomeBottomNavigationItem,
    val isSelected: Boolean = false,
)
