package com.hrudhaykanth116.todo.domain.use_cases

import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.model.TodoUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: com.hrudhaykanth116.todo.data.repositories.TodoRepository
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(todoUIModel: TodoUIModel){
        todoRepository.createTodoTask(
            todoUIModel.id,
            todoUIModel.title,
            todoUIModel.description,
            todoUIModel.category,
            todoUIModel.completed,
        )
    }

}