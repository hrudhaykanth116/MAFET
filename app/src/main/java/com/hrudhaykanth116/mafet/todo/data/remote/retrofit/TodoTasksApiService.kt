package com.hrudhaykanth116.mafet.todo.data.remote.retrofit

import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.mafet.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.mafet.todo.data.remote.models.PostTodoResponse
import retrofit2.Response


interface TodoTasksApiService {

    suspend fun getTodoTasks(

    ): Response<GetTodoResponse>

    suspend fun postTodoTask(): Response<PostTodoResponse>

}