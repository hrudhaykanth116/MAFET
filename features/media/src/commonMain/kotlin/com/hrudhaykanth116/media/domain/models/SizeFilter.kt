package com.hrudhaykanth116.media.domain.models

enum class SizeFilter(val value: String?) {
    ALL(null),
    LARGE("large"),
    MEDIUM("medium"),
    SMALL("small");

    companion object {
        fun fromValue(value: String?): SizeFilter {
            return entries.find { it.value == value } ?: ALL
        }
    }
}
