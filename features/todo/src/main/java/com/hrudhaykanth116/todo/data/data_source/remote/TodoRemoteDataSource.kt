package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.data.remote.models.CreateTodoRequest
import com.hrudhaykanth116.todo.data.remote.retrofit.TodoTasksApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRemoteDataSource @Inject constructor(
    private val todoTasksApiService: TodoTasksApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): com.hrudhaykanth116.core.data.remote.NetworkDataSource(), ITodoRemoteDataSource {

    override suspend fun getTodoTasks(): com.hrudhaykanth116.core.data.models.DataResult<GetTodoResponse> = withContext(
        dispatcher
    ){
        val apiResponse: com.hrudhaykanth116.core.data.models.DataResult<GetTodoResponse> = getResult {
            todoTasksApiService.getTodoTasks()
        }
        return@withContext apiResponse
    }

    override suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: String,
        active: Boolean
    ): com.hrudhaykanth116.core.data.models.DataResult<PostTodoResponse> = withContext(dispatcher) {
        getResult {
            todoTasksApiService.postTodoTask(
                CreateTodoRequest(
                    id = id,
                    title = title,
                    description = description,
                    category = category,
                    active = active,
                )
            )
        }
    }



}