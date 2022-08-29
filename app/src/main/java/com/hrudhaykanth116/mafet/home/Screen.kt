package com.hrudhaykanth116.mafet.home

sealed class Screen(val route: String){
    object HomeScreen: Screen("Home_screen")
    object Todo: Screen("todo")
    object Auth: Screen("auth")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }
}