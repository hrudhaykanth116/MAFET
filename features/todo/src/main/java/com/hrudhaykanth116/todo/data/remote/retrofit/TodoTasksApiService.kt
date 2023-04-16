package com.hrudhaykanth116.todo.data.remote.retrofit

import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import retrofit2.Response


interface TodoTasksApiService {

    suspend fun getTodoTasks(

    ): Response<GetTodoResponse>

    suspend fun postTodoTask(): Response<PostTodoResponse>

}