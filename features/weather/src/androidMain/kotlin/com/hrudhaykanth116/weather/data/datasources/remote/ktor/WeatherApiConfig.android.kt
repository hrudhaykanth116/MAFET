package com.hrudhaykanth116.weather.data.datasources.remote.ktor

import com.hrudhaykanth116.weather.BuildConfig

actual object WeatherApiConfig {
    actual val openWeatherApiKey: String = BuildConfig.OPEN_WEATHER_FORECAST_API_KEY
}
