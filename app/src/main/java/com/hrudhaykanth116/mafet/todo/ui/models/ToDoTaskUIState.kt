package com.hrudhaykanth116.mafet.todo.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hrudhaykanth116.mafet.todo.domain.model.TodoUIModel

data class ToDoTaskUIState(
    val data: TodoUIModel,
    // This acts as an observable state for composables
    val isExpanded: MutableState<Boolean> = mutableStateOf(false),
){

}