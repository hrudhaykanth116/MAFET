package com.hrudhaykanth116.mafet.todo.ui.data_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ToDoTaskUIState(
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