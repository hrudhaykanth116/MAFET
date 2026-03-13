package com.hrudhaykanth116.todo.domain.model

enum class TaskCategory(val key: String) {
    GENERAL("General"),
    WORK("Work"),
    PERSONAL("Personal"),
    SHOPPING("Shopping"),
    HEALTH("Health"),
    OTHER("Other");

    companion object {
        val DEFAULT = GENERAL

        fun fromKey(value: String?): TaskCategory {
            if (value.isNullOrBlank()) return DEFAULT
            return entries.find { it.key.equals(value, ignoreCase = true) } ?: DEFAULT
        }
    }
}