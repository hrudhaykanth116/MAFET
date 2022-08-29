package com.hrudhaykanth116.mafet.todo.data.remote.retrofit

import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import retrofit2.Response


interface TodoTasksApiService {

    suspend fun getTodoTasks(

    ): Response<List<TodoTask>>

}