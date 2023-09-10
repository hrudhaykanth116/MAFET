package com.hrudhaykanth116.mafet.home.models

import com.hrudhaykanth116.core.R as CoreR


enum class HomeBottomNavigationItem(val displayName: String,val iconDrawable: Int, val route: String,) {

    TODO("Todo", CoreR.drawable.ic_clipboard, HomeRoute.Todo.route),
    WEATHER("Weather", CoreR.drawable.ic_weather, HomeRoute.Weather.route),
    // TRANSLATE("Translate", CoreR.drawable.ic_translate, HomeRoute.Translate.route),
    // Dictionary("Dictionary", CoreR.drawable.ic_dictionary, HomeRoute.Dictionary.route),
    ENTERTAINMENT("Tv", CoreR.drawable.ic_tv, HomeRoute.Entertainment.route),
    ACCOUNT("Account", CoreR.drawable.ic_account, HomeRoute.Account.route),

}