package com.hrudhaykanth116.tv.data.models.constants

import com.hrudhaykanth116.tv.BuildConfig

class MoviesDbConstants {

    companion object{
        const val API_KEY = BuildConfig.TMDB_API_KEY
        const val IMAGES_BASE_URL = "http://image.tmdb.org/t/p/original/"
        const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
        const val VIMEO_BASE_URL = "https://vimeo.com/"
    }

}