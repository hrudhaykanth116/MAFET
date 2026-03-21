package com.hrudhaykanth116.todo.ui.models

import com.hrudhaykanth116.todo.domain.model.TodoDefaults

enum class TodoListScreenSortItem(val key: String, val displayName: String) {
    PRIORITY(TodoDefaults.SORT_BY_PRIORITY, "Priority"),
    TARGET_TIME(TodoDefaults.SORT_BY_TARGET_TIME, "Target time"),
}