package com.hrudhaykanth116.todo.domain.model

enum class TaskCategory(
    val id: String,
    val displayName: String
) {

    GENERAL("1", "General");

    companion object {

        fun fromId(id: String): TaskCategory?{
            return values().find {
                it.id == id
            }
        }

        fun toId(taskCategory: TaskCategory): String? {
            return values().find {
                it == taskCategory
            }?.id
        }

    }

}