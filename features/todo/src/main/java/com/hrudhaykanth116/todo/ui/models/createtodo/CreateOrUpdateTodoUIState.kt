package com.hrudhaykanth116.todo.ui.models.createtodo

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.todo.domain.model.TASK_CATEGORY_DEFAULT_NAME
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

data class CreateOrUpdateTodoUIState(
    val todoUIModel: TodoUIModel = TodoUIModel(),

    val isInEditMode: Boolean = true,

    val taskId: String? = null,

    val titleError: String? = null,

    val isSubmitted: Boolean = false,

    val userMessage: UserMessage? = null,

)