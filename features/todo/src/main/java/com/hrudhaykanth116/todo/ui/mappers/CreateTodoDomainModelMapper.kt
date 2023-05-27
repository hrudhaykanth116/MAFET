package com.hrudhaykanth116.todo.ui.mappers

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState

fun CreateOrUpdateTodoDomainModel.toUIModel(): CreateOrUpdateTodoUIState {

    val titleError = if (titleError == ErrorState.EMPTY_FIELD) {
        "Title cannot be empty"
    } else {
        // TODO: See how to handle error state mapping
        ""
        // throw IllegalArgumentException("Error state $titleError is not handled.")
    }

    return CreateOrUpdateTodoUIState(
        todoUIModel = TodoUIModel(
            id = id,
            title = TextFieldValue(title),
            description = TextFieldValue(description)
        ),
        titleError = titleError,
        isInEditMode = isInEditMode,
        isSubmitted = isSubmitted
    )

}

fun CreateOrUpdateTodoUIState.toDomainModel(): CreateOrUpdateTodoDomainModel {
    return CreateOrUpdateTodoDomainModel(
        isInEditMode = isInEditMode,
        title = todoUIModel.title.text,
        // titleError = titleError,
        description = todoUIModel.description.text,
        category = category,
        isSubmitted = isSubmitted
    )
}