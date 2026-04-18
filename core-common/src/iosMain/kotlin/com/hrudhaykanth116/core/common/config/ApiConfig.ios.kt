package com.hrudhaykanth116.core.common.config

import platform.Foundation.NSBundle

actual object ApiConfig {
    actual val openWeatherApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("OPEN_WEATHER_FORECAST_API_KEY") as? String ?: ""

    actual val tmdbApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_API_KEY") as? String ?: ""

    actual val pexelsApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("PEXELS_API_KEY") as? String ?: ""
}
