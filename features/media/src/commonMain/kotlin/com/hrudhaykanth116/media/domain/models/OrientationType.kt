package com.hrudhaykanth116.media.domain.models

enum class OrientationType(val value: String?) {
    ALL(null),
    PORTRAIT("portrait"),
    LANDSCAPE("landscape"),
    SQUARE("square");

    companion object {
        fun fromValue(value: String?): OrientationType {
            return entries.find { it.value == value } ?: ALL
        }
    }
}
