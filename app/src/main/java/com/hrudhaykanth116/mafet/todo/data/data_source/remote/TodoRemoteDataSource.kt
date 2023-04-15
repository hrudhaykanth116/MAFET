package com.hrudhaykanth116.mafet.todo.data.data_source.remote

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.remote.NetworkDataSource
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.mafet.todo.data.remote.models.GetTodoResponse
import com.hrudhaykanth116.mafet.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.mafet.todo.data.remote.retrofit.TodoTasksApiService
import com.hrudhaykanth116.mafet.todo.domain.model.TaskCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
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
        category: TaskCategory,
        active: Boolean
    ): com.hrudhaykanth116.core.data.models.DataResult<PostTodoResponse> {
        return getResult {
            todoTasksApiService.postTodoTask()
        }
    }



}