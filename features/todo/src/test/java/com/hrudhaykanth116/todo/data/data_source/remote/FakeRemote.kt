package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse

class FakeRemote(private val succeed: Boolean) : ITodoRemoteDataSource {
    override suspend fun getTodoTasks(): DataResult<GetTodoResponse> =
        DataResult.Success(GetTodoResponse(200, "ok", GetTodoResponse.TodoData()))

    override suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: String,
        active: Boolean
    ): DataResult<PostTodoResponse> {
        return if (succeed) {
            DataResult.Success(PostTodoResponse(200, "ok", PostTodoResponse.TodoData(id = id)))
        } else {
            DataResult.Error()
        }
    }
}