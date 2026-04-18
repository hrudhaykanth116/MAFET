package com.hrudhaykanth116.core.common.config

import com.hrudhaykanth116.core.common.DesktopBuildConfig

actual object ApiConfig {
    actual val openWeatherApiKey: String = DesktopBuildConfig.OPEN_WEATHER_FORECAST_API_KEY
    actual val tmdbApiKey: String = DesktopBuildConfig.TMDB_API_KEY
    actual val pexelsApiKey: String = DesktopBuildConfig.PEXELS_API_KEY
}
