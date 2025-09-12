package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse

interface ITodoRemoteDataSource {

    suspend fun getTodoTasks(): ApiResultWrapper<GetTodoResponse>

    suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: String,
        active: Boolean
    ): ApiResultWrapper<PostTodoResponse>
} 