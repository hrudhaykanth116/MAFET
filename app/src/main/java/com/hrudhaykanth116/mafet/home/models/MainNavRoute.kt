package com.hrudhaykanth116.mafet.home.models

sealed class MainNavRoute(val route: String){
    object HomeRoute: MainNavRoute("Home_screen")
    object Todo: MainNavRoute("todo")
    object Auth: MainNavRoute("auth")
    object Account: MainNavRoute("account")
    object Weather: MainNavRoute("weather")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }
}