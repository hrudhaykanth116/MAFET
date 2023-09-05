package com.hrudhaykanth116.mafet.home.models

data class HomeScreenState(

    val bottomNavItemsState: BottomNavItemState = BottomNavItemState()

)

data class BottomNavItemState(
    val selectedIndex: Int = 0
)