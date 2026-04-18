package com.hrudhaykanth116.core.common.config

import com.hrudhaykanth116.core.common.BuildConfig

actual object ApiConfig {
    actual val openWeatherApiKey: String = BuildConfig.OPEN_WEATHER_FORECAST_API_KEY
    actual val tmdbApiKey: String = BuildConfig.TMDB_API_KEY
    actual val pexelsApiKey: String = BuildConfig.PEXELS_API_KEY
}
