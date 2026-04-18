package com.hrudhaykanth116.tv.data.models.constants

import com.hrudhaykanth116.core.common.config.ApiConfig

class MoviesDbConstants {

    companion object {
        val API_KEY: String get() = ApiConfig.tmdbApiKey
        const val IMAGES_BASE_URL = "http://image.tmdb.org/t/p/original/"
        const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
        const val VIMEO_BASE_URL = "https://vimeo.com/"
    }

}