package com.hrudhaykanth116.mafet.home

sealed class MainNavScreen(val route: String){
    object HomeScreen: MainNavScreen("Home_screen")
    object Todo: MainNavScreen("todo")
    object Auth: MainNavScreen("auth")
    object Account: MainNavScreen("account")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }
}