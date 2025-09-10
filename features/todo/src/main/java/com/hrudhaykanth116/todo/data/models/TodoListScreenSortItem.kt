package com.hrudhaykanth116.todo.data.models

enum class TodoListScreenSortItem(val key: String, val displayName: String) {
    // CATEGORY("Category"),
    PRIORITY("priority", "Priority"),
    TARGET_TIME("targetTime", "Target time"),
    // CREATED_TIME("Priority"),
}