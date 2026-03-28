package com.hrudhaykanth116.tv.ui

import androidx.compose.ui.graphics.Color

object TvColors {
    // Status colors for show progress
    val WatchingActive = Color(0xFF42A5F5)    // Blue - Currently watching
    val Completed = Color(0xFF66BB6A)          // Green - Finished
    val OnHold = Color(0xFFFFB74D)             // Orange - Paused
    val PlanToWatch = Color(0xFFAB47BC)        // Purple - In queue

    // Genre colors
    val GenreAction = Color(0xFFE57373)
    val GenreDrama = Color(0xFF9575CD)
    val GenreComedy = Color(0xFFFFD54F)
    val GenreSciFi = Color(0xFF4FC3F7)
    val GenreThriller = Color(0xFFEF5350)
    val GenreDefault = Color(0xFF78909C)

    fun getGenreColor(genre: String): Color {
        return when (genre.lowercase()) {
            "action" -> GenreAction
            "drama" -> GenreDrama
            "comedy" -> GenreComedy
            "sci-fi", "scifi", "science fiction" -> GenreSciFi
            "thriller", "mystery" -> GenreThriller
            else -> GenreDefault
        }
    }
}
