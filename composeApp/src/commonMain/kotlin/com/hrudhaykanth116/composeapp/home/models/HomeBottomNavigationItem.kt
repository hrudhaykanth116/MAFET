package com.hrudhaykanth116.composeapp.home.models

import org.jetbrains.compose.resources.DrawableResource
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_clipboard
import mafet.core_ui.generated.resources.ic_diary
import mafet.core_ui.generated.resources.ic_weather
import mafet.core_ui.generated.resources.ic_tv
import mafet.core_ui.generated.resources.ic_pictures


enum class HomeBottomNavigationItem(val displayName: String,val iconDrawable: DrawableResource, val route: String) {

    TODO("Todo", Res.drawable.ic_clipboard, HomeRoute.Todo.route),

    JOURNAL("Journal", Res.drawable.ic_diary, HomeRoute.Journal.route),

    WEATHER("Weather", Res.drawable.ic_weather, HomeRoute.Weather.route),

    ENTERTAINMENT("Tv", Res.drawable.ic_tv, HomeRoute.Entertainment.route),

    MEDIA("Media", Res.drawable.ic_pictures, HomeRoute.Media.route),

}
