package com.hrudhaykanth116.todo.data.remote.retrofit

import com.hrudhaykanth116.todo.data.remote.models.CreateTodoRequest
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoTasksApiService {

    @GET("todos")
    suspend fun getTodoTasks(): Response<GetTodoResponse>

    @POST("todos")
    suspend fun postTodoTask(
        @Body request: CreateTodoRequest
    ): Response<PostTodoResponse>
}

