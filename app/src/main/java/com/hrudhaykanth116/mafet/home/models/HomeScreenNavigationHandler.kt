package com.hrudhaykanth116.mafet.home.models

import android.content.Context
import androidx.navigation.NavHostController
import com.hrudhaykanth116.weather.ui.screens.WeatherActivity

class HomeScreenNavigationHandler(
    context: Context,
    navController: NavHostController,
) {

    val onTodoClicked = {
        navController.navigate(
            HomeRoute.Todo.route
        )
    }

    val onAccountClicked = {
        navController.navigate(
            HomeRoute.Account.route
        ) {
            popUpTo(0)
        }
    }

    val onWeatherClicked = {
        // navController.navigate(
        //     MainNavRoute.Weather.route
        // )

        WeatherActivity.start(context)

    }



}