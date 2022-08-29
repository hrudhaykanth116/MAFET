package com.hrudhaykanth116.mafet.todo.domain.use_cases

import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import com.hrudhaykanth116.mafet.todo.data.repositories.TodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoTaskUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(todoTask: TodoTask) = withContext(
        dispatcher
    ){
        todoRepository.createTodoTask(todoTask)
    }

}