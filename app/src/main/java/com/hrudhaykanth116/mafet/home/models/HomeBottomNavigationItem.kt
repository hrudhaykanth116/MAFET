package com.hrudhaykanth116.mafet.home.models

import org.jetbrains.compose.resources.DrawableResource
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_clipboard
import mafet.core_ui.generated.resources.ic_diary
import mafet.core_ui.generated.resources.ic_weather
import mafet.core_ui.generated.resources.ic_tv
import mafet.core_ui.generated.resources.ic_pictures
import mafet.core_ui.generated.resources.ic_account


enum class HomeBottomNavigationItem(val displayName: String,val iconDrawable: DrawableResource, val route: String) {

    // Create/update pending tasks with priority, target time
    TODO("Todo", Res.drawable.ic_clipboard, HomeRoute.Todo.route),

    JOURNAL("Journal", Res.drawable.ic_diary, HomeRoute.Journal.route),

    // Shows today's weather for a search field and 7 days forecast.
    WEATHER("Weather", Res.drawable.ic_weather, HomeRoute.Weather.route),

    // Contains subscriptions and coupons for anything
    // SUBSCRIPTIONS("Subscriptions", CoreR.drawable.ic_weather, HomeRoute.Weather.route),

    // Translate from any language to desired language.
    // TRANSLATE("Translate", CoreR.drawable.ic_translate, HomeRoute.Translate.route),

    // Shows a word's synonym, antonym, usages etc..
    // Dictionary("Dictionary", CoreR.drawable.ic_dictionary, HomeRoute.Dictionary.route),

    // Track tv shows, episodes, fav scene/dialog
    ENTERTAINMENT("Tv", Res.drawable.ic_tv, HomeRoute.Entertainment.route),

    // Images and Videos
    MEDIA("Media", Res.drawable.ic_pictures, HomeRoute.Media.route),

    // Logout, App github link, Credits
    ACCOUNT("Account", Res.drawable.ic_account, HomeRoute.Account.route),

}