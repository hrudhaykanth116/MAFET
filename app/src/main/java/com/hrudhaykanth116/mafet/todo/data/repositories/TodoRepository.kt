package com.hrudhaykanth116.mafet.todo.data.repositories

import com.hrudhaykanth116.mafet.common.remote.BaseRemoteDataSource
import com.hrudhaykanth116.mafet.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.mafet.todo.data.data_source.remote.TodoRemoteDataSource
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    // TODO: Follow dependency inversion principle
    private val todoLocalDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
) {

    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTodoTask(): List<TodoTask> {
        return todoLocalDataSource.getTodoTasks()
    }

    suspend fun createTodoTask(todoTask: TodoTask){
        todoLocalDataSource.createTodoTask(todoTask)
    }

}