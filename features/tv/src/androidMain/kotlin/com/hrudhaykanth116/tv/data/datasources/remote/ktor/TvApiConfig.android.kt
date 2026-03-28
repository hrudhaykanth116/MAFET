package com.hrudhaykanth116.tv.data.datasources.remote.ktor

import com.hrudhaykanth116.tv.BuildConfig

actual object TvApiConfig {
    actual val tmdbApiKey: String = BuildConfig.TMDB_API_KEY
}
