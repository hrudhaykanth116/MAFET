package com.hrudhaykanth116.todo.domain.model.create

import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.todo.domain.model.TASK_CATEGORY_DEFAULT_NAME

data class CreateOrUpdateTodoDomainModel(
    val id : String? = null,
    val title: String = "",
    val titleError: ErrorState? = null,

    val description: String = "",

    val category: String = TASK_CATEGORY_DEFAULT_NAME,

    val priority: Int? = null,
    val targetTime: Long? = null,

    val isInEditMode: Boolean = true,
    val isSubmitted: Boolean = false,
) {

    fun getStateAfterValidation(): CreateOrUpdateTodoDomainModel {

        val titleError = if (title.isBlank()) ErrorState.EMPTY_FIELD else null

        return copy(
            titleError = titleError
        )
    }

    fun containsError(): Boolean{
        return titleError != null
    }

}

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