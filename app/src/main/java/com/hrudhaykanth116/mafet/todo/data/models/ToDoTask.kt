package com.hrudhaykanth116.mafet.todo.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ToDoTask(
    val id: Long,
    val title: String,
    val description: String? = null,
    val isActive: Boolean = true,
    val category: String = "General",
    // This acts as an observable state for composables
    val isExpanded: MutableState<Boolean> = mutableStateOf(false)
){

    enum class TaskStatus{

        ACTIVE,
        COMPLETED,

    }

}