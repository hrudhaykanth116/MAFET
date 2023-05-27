package com.hrudhaykanth116.todo.data.remote.models

import com.hrudhaykanth116.core.utils.enumutils.asEnumOrDefault
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

data class GetTodoResponse(
    val code: Int,
    val message: String,
    val data: TodoData,
) {
    data class TodoData(
        val id: Long? = null,
        val title: String? = null,
        val description: String? = null,
        val completed: Boolean? = null,
        val category: String? = null,
    )
}