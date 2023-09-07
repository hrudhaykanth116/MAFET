package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.weather.databinding.FragmentWeatherHomeScreenBinding

@Composable
fun WeatherNavigation(
    modifier: Modifier = Modifier,
) {

    // If this dependency is added other issues are arising. need to update code elsewhere
    // AndroidViewBinding(FragmentWeatherHomeScreenBinding::inflate) {
    //
    // }

    WeatherHomeScreen(modifier)


}