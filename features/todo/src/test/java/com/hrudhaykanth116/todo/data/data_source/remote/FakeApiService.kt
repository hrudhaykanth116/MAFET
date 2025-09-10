package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.todo.data.remote.models.CreateTodoRequest
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.data.remote.retrofit.TodoTasksApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeApiService(
    private val shouldSucceed: Boolean,
) : TodoTasksApiService {
    override suspend fun getTodoTasks(): Response<GetTodoResponse> {
        return if (shouldSucceed) {
            Response.success(GetTodoResponse(200, "ok", GetTodoResponse.TodoData()))
        } else {
            Response.error(
                500,
                "{}".toResponseBody(contentType = "application/json".toMediaType())
            )
        }
    }

    override suspend fun postTodoTask(request: CreateTodoRequest): Response<PostTodoResponse> {
        return if (shouldSucceed) {
            Response.success(
                PostTodoResponse(
                    200,
                    "ok",
                    PostTodoResponse.TodoData(id = request.id)
                )
            )
        } else {
            // Response.error(400, "{}".toResponseBody("application/json".toMediaType()))
            Response.error(400, "{}".toResponseBody(null))
        }
    }
}