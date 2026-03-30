package com.hrudhaykanth116.tv.data.datasources.local.models

enum class WatchStatus(
    val displayName: String,
    val colorValue: Long
) {
    WATCHING("Watching", 0xFF4CAF50),
    COMPLETED("Completed", 0xFF2196F3),
    ON_HOLD("On Hold", 0xFFFF9800),
    DROPPED("Dropped", 0xFFF44336),
    PLAN_TO_WATCH("Plan to Watch", 0xFF9C27B0);

    companion object {
        fun fromOrdinal(ordinal: Int?): WatchStatus? {
            return ordinal?.let { entries.getOrNull(it) }
        }
    }
}
