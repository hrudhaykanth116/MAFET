package com.hrudhaykanth116.mafet.main

sealed class Screen(val route: String){
    object HomeScreen: Screen("Home_screen")


    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }
}