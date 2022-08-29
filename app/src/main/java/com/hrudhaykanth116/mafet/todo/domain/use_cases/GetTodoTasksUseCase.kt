package com.hrudhaykanth116.mafet.todo.domain.use_cases

import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import com.hrudhaykanth116.mafet.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.mafet.todo.ui.data_models.ToDoTaskUIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTodoTasksUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    suspend operator fun invoke(): List<TodoTask>{
        val todoTasksList: List<TodoTask> = todoRepository.getTodoTask()
        return todoTasksList
    }

}