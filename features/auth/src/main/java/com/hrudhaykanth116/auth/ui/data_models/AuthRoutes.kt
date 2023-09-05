package com.hrudhaykanth116.auth.ui.data_models

sealed class AuthRoutes(val route: String) {
    object LoginRoute : AuthRoutes("login_screen")
    object SignUpRoute : AuthRoutes("sign_up_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }

}