package com.hrudhaykanth116.mafet.home.models

import com.hrudhaykanth116.core.R as CoreR


enum class HomeBottomNavigationItem(val displayName: String,val iconDrawable: Int, val route: String,) {

    // Create/update pending tasks with priority, target time
    TODO("Todo", CoreR.drawable.ic_clipboard, HomeRoute.Todo.route),

    // Shows today's weather for a search field and 7 days forecast.
    WEATHER("Weather", CoreR.drawable.ic_weather, HomeRoute.Weather.route),

    // Contains subscriptions and coupons for anything
    // SUBSCRIPTIONS("Subscriptions", CoreR.drawable.ic_weather, HomeRoute.Weather.route),

    // Translate from any language to desired language.
    // TRANSLATE("Translate", CoreR.drawable.ic_translate, HomeRoute.Translate.route),

    // Shows a word's synonym, antonym, usages etc..
    // Dictionary("Dictionary", CoreR.drawable.ic_dictionary, HomeRoute.Dictionary.route),

    // Track tv shows, episodes, fav scene/dialog
    ENTERTAINMENT("Tv", CoreR.drawable.ic_tv, HomeRoute.Entertainment.route),

    // Logout, App github link, Credits
    ACCOUNT("Account", CoreR.drawable.ic_account, HomeRoute.Account.route),

}