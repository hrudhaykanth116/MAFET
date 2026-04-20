package com.hrudhaykanth116.composeapp.home.models

import com.hrudhaykanth116.composeapp.models.Feature
import org.jetbrains.compose.resources.DrawableResource
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_account
import mafet.core_ui.generated.resources.ic_clipboard
import mafet.core_ui.generated.resources.ic_dashboard
import mafet.core_ui.generated.resources.ic_diary
import mafet.core_ui.generated.resources.ic_weather
import mafet.core_ui.generated.resources.ic_tv
import mafet.core_ui.generated.resources.ic_pictures
import mafet.core_ui.generated.resources.ic_genie


enum class HomeBottomNavigationItem(val displayName: String,val iconDrawable: DrawableResource, val route: String) {

    DASHBOARD("Home", Res.drawable.ic_dashboard, HomeRoute.Dashboard.route),

    TODO("Todo", Res.drawable.ic_clipboard, HomeRoute.Todo.route),

    JOURNAL("Journal", Res.drawable.ic_diary, HomeRoute.Journal.route),

    AI("AI", Res.drawable.ic_genie, HomeRoute.AI.route),

    WEATHER("Weather", Res.drawable.ic_weather, HomeRoute.Weather.route),

    ENTERTAINMENT("Tv", Res.drawable.ic_tv, HomeRoute.Entertainment.route),

    MEDIA("Media", Res.drawable.ic_pictures, HomeRoute.Media.route);

    companion object{

        fun getFromKey(feature: Feature): HomeBottomNavigationItem?{
            return when(feature){
                Feature.TODO -> TODO
                Feature.JOURNAL -> JOURNAL
                Feature.AI -> AI
                Feature.WEATHER -> WEATHER
                Feature.WATCHLIST -> ENTERTAINMENT
                Feature.MEDIA -> MEDIA
            }
        }

    }

}
