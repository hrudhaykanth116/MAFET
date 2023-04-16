package com.hrudhaykanth116.todo.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hrudhaykanth116.todo.domain.model.TodoUIModel

data class ToDoTaskUIState(
    val data: com.hrudhaykanth116.todo.domain.model.TodoUIModel,
    // This acts as an observable state for composables
    val isExpanded: MutableState<Boolean> = mutableStateOf(false),
){

}