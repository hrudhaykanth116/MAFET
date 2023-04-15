package com.hrudhaykanth116.mafet.todo.data.remote.models

import com.hrudhaykanth116.core.utils.enumutils.asEnumOrDefault
import com.hrudhaykanth116.mafet.todo.domain.model.TaskCategory
import com.hrudhaykanth116.mafet.todo.domain.model.TodoUIModel

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
    ){

        fun toUIModel(): TodoUIModel {
            return TodoUIModel(
                id = id ?: -1L,
                title = title ?: "No title",
                description = description ?: "No description",
                completed = completed == false,
                category = category.asEnumOrDefault(TaskCategory.GENERAL)
            )
        }

    }
}