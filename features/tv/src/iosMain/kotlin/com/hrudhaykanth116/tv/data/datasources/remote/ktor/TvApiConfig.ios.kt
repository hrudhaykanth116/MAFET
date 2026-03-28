package com.hrudhaykanth116.tv.data.datasources.remote.ktor

import platform.Foundation.NSBundle

actual object TvApiConfig {
    actual val tmdbApiKey: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_API_KEY") as? String ?: ""
}
