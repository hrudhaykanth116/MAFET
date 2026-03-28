package com.hrudhaykanth116.tv.domain.models

enum class TvCategory(val displayName: String, val routeParam: String) {
    POPULAR("Popular", "popular"),
    TRENDING("Trending", "trending"),
    AIRING_TODAY("Airing Today", "airing_today"),
    TOP_RATED("Top Rated", "top_rated");

    companion object {
        fun fromRouteParam(param: String): TvCategory? {
            return entries.find { it.routeParam == param }
        }
    }
}
