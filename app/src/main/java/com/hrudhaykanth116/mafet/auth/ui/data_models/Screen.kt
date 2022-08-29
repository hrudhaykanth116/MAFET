package com.hrudhaykanth116.mafet.auth.ui.data_models

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
}