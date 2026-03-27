package com.hrudhaykanth116.weather.data.datasources.remote.ktor

import platform.Foundation.NSBundle

actual object WeatherApiConfig {
    actual val geoCodingApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("OPEN_WEATHER_GEO_CODING_API_KEY") as? String ?: ""
    actual val forecastApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("OPEN_WEATHER_FORECAST_API_KEY") as? String ?: ""
}
