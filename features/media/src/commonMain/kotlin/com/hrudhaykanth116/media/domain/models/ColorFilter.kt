package com.hrudhaykanth116.media.domain.models

enum class ColorFilter(val value: String?, val displayName: String, val hexColor: String) {
    ALL(null, "All", "#FFFFFF"),
    RED("red", "Red", "#F44336"),
    ORANGE("orange", "Orange", "#FF9800"),
    YELLOW("yellow", "Yellow", "#FFEB3B"),
    GREEN("green", "Green", "#4CAF50"),
    TURQUOISE("turquoise", "Turquoise", "#00BCD4"),
    BLUE("blue", "Blue", "#2196F3"),
    VIOLET("violet", "Violet", "#9C27B0"),
    PINK("pink", "Pink", "#E91E63"),
    BROWN("brown", "Brown", "#795548"),
    BLACK("black", "Black", "#000000"),
    GRAY("gray", "Gray", "#9E9E9E"),
    WHITE("white", "White", "#FFFFFF");

    companion object {
        fun fromValue(value: String?): ColorFilter {
            return entries.find { it.value == value } ?: ALL
        }
    }
}
