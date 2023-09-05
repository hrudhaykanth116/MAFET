package com.hrudhaykanth116.mafet.home.models

sealed class HomeRoute(val route: String){
    object Todo: HomeRoute("todo")
    object Weather: HomeRoute("weather")
    object Translate: HomeRoute("translate")
    object Dictionary: HomeRoute("dictionary")
    object Entertainment: HomeRoute("entertainment")
    object Account: HomeRoute("account")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                append("/${it}")
            }
        }
    }

    companion object{

        fun getRoutes(): List<HomeRoute> {
            return listOf<HomeRoute>(
                Todo,
                Weather,
                Translate,
                Dictionary,
                Entertainment,
                Account
            )
        }

    }

}