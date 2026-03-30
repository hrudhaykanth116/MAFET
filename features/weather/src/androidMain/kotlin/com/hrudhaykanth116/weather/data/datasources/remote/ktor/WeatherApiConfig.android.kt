package com.hrudhaykanth116.weather.data.datasources.remote.ktor

import com.hrudhaykanth116.weather.BuildConfig

actual object WeatherApiConfig {
    actual val geoCodingApiKey: String = BuildConfig.OPEN_WEATHER_GEO_CODING_API_KEY
    actual val forecastApiKey: String = BuildConfig.OPEN_WEATHER_FORECAST_API_KEY
}
