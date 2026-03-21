package com.hrudhaykanth116.todo.domain.model

enum class SyncStatus(val key: String) {
    SYNCED("synced"),
    PENDING_CREATE("pending_create"),
    PENDING_UPDATE("pending_update"),
    PENDING_DELETE("pending_delete");

    companion object {
        val DEFAULT = SYNCED

        fun fromKey(value: String?): SyncStatus {
            if (value.isNullOrBlank()) return DEFAULT
            return entries.find { it.key.equals(value, ignoreCase = true) } ?: DEFAULT
        }
    }
}
