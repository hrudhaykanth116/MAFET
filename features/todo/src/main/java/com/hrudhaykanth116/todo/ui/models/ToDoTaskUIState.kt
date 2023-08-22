package com.hrudhaykanth116.todo.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ToDoTaskUIState(
    val data: TodoUIModel,
    // This acts as an observable state for composables
    val isExpanded: MutableState<Boolean> = mutableStateOf(false),
    val showCategoryIcon: Boolean = false,
){

}