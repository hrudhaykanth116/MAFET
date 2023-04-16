package com.hrudhaykanth116.mafet.auth.ui.data_models

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }

}