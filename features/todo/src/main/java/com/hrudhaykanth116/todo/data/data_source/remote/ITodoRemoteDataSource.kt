package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse

interface ITodoRemoteDataSource {

    suspend fun getTodoTasks(): DataResult<GetTodoResponse>

    suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: String,
        active: Boolean
    ): DataResult<PostTodoResponse>
} 