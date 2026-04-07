package com.hrudhaykanth116.composeapp.home.models

data class HomeBottomNavigationUIState(
    val list: List<NavigationItemUIState>
)

data class NavigationItemUIState(
    val homeBottomNavigationItem: HomeBottomNavigationItem,
    val isSelected: Boolean = false,
)
