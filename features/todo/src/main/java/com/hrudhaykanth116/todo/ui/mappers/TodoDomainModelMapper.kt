package com.hrudhaykanth116.todo.ui.mappers

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

fun TodoModel.toUIModel(): TodoUIModel {

    return TodoUIModel(
        id = id,
        title = TextFieldValue(title),
        description = TextFieldValue(description)
    )

}

// TODO: Wrong. Do something
fun TodoModel.toState(): ToDoTaskUIState{
    return ToDoTaskUIState(
        data = this.toUIModel(),
    )
}