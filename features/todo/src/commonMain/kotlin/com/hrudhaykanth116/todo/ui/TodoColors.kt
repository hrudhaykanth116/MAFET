package com.hrudhaykanth116.todo.ui

import androidx.compose.ui.graphics.Color

object TodoColors {
    val PriorityVeryLow = Color(0xFF81C784)
    val PriorityLow = Color(0xFFAED581)
    val PriorityMedium = Color(0xFFFFD54F)
    val PriorityHigh = Color(0xFFFFB74D)
    val PriorityVeryHigh = Color(0xFFE57373)

    val CategoryWork = Color(0xFF42A5F5)
    val CategoryPersonal = Color(0xFFAB47BC)
    val CategoryShopping = Color(0xFF26A69A)
    val CategoryHealth = Color(0xFFEF5350)
    val CategoryGeneral = Color(0xFF78909C)

    fun getPriorityColor(priority: Int): Color {
        return when (priority) {
            1 -> PriorityVeryLow
            2 -> PriorityLow
            3 -> PriorityMedium
            4 -> PriorityHigh
            5 -> PriorityVeryHigh
            else -> PriorityMedium
        }
    }

    fun getCategoryColor(category: String): Color {
        return when (category.lowercase()) {
            "work" -> CategoryWork
            "personal" -> CategoryPersonal
            "shopping" -> CategoryShopping
            "health" -> CategoryHealth
            else -> CategoryGeneral
        }
    }
}
