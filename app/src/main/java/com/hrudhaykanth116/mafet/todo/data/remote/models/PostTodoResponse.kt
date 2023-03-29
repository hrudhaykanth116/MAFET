package com.hrudhaykanth116.mafet.todo.data.remote.models

import com.hrudhaykanth116.mafet.common.utils.enumutils.asEnumOrDefault
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.mafet.todo.domain.model.TaskCategory
import com.hrudhaykanth116.mafet.todo.domain.model.TodoUIModel

data class PostTodoResponse(
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
                completed = completed ?: false,
                category = category.asEnumOrDefault(TaskCategory.GENERAL)
            )
        }

        fun toDbEntity(): TodoTaskDbEntity{
            val entity = TodoTaskDbEntity(
                id = id!!,
                title = title!!,
                description = description,
                completed = completed!!,
                category = category!!
            )
            return entity
        }

    }
}