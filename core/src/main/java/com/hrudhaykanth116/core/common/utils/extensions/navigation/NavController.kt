package com.hrudhaykanth116.core.common.utils.extensions.navigation

import androidx.navigation.NavController

fun NavController.navigateClearBackStack(
    route: String
) {
    navigate(
        route = route
    ) {
        popUpTo(0)
    }
}