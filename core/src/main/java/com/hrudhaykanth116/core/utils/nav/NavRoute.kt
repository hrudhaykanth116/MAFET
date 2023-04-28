package com.hrudhaykanth116.core.utils.nav

import android.os.Bundle

open class NavRoute<T>(val route: String) {

    fun getRoute(args: T? = null): String{
        return route
    }

    fun getArgs(arguments: Bundle?): T?{
        return null
    }

}