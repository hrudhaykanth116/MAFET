package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.data.remote.retrofit.TodoTasksApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRemoteDataSource @Inject constructor(
    private val todoTasksApiService: TodoTasksApiService,
): com.hrudhaykanth116.core.data.remote.NetworkDataSource() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTodoTasks(): com.hrudhaykanth116.core.data.models.DataResult<GetTodoResponse> = withContext(
        dispatcher
    ){
        val apiResponse: com.hrudhaykanth116.core.data.models.DataResult<GetTodoResponse> = getResult {
            todoTasksApiService.getTodoTasks()
        }
        return@withContext apiResponse
    }

    suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: String,
        active: Boolean
    ): com.hrudhaykanth116.core.data.models.DataResult<PostTodoResponse> {
        return getResult {
            todoTasksApiService.postTodoTask()
        }
    }



}