package com.hrudhaykanth116.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

// TODO: Use a base abstraction
abstract class NavigationCommand {
    abstract val route: String
    abstract val arguments: List<NamedNavArgument>
    abstract val deepLinks: List<NavDeepLink>
}