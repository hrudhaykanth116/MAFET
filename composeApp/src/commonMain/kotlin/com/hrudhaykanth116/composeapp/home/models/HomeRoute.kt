package com.hrudhaykanth116.composeapp.home.models

sealed class HomeRoute(val route: String){
    object Dashboard: HomeRoute("dashboard")
    object Todo: HomeRoute("todo")
    object Weather: HomeRoute("weather")
    object Media: HomeRoute("media")
    object Translate: HomeRoute("translate")
    object Journal: HomeRoute("journal")
    object Dictionary: HomeRoute("dictionary")
    object Entertainment: HomeRoute("entertainment")
    object AI: HomeRoute("ai")

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
                Dashboard,
                Todo,
                Weather,
                Media,
                Translate,
                Journal,
                Dictionary,
                Entertainment,
                AI
            )
        }

    }

}
