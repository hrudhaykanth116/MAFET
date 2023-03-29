package com.hrudhaykanth116.mafet.common.extensions.navigation

import androidx.navigation.NavController
import com.hrudhaykanth116.mafet.home.MainNavScreen

fun NavController.navigateClearBackStack(
    route: String
) {
    navigate(
        route = route
    ) {
        popUpTo(0)
    }
}