package com.hrudhaykanth116.todo.domain.model.create

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.UIState

data class CreateTodoUIState(

    val title: TextFieldValue = TextFieldValue(),
    val titleError: UIText? = null,

    val description: TextFieldValue = TextFieldValue(),

    val isSubmitted: Boolean = false,

)

// class CreateTodoLoadingState(
//     // val createTodo: CreateTodoUIState? = null,
// ): UIState.LoadingUIState()
//
// class CreateTodoLoadedState(
//     val createTodo: CreateTodoUIState,
// ): UIState.LoadedUIState<CreateTodoUIState>(createTodo)
//
// class CreateTodoErrorState(
//     val error: UIText,
// ): UIState.ErrorUIState(error)