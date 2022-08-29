package com.hrudhaykanth116.mafet.todo.data.data_source.remote

import com.hrudhaykanth116.mafet.common.remote.BaseRemoteDataSource
import com.hrudhaykanth116.mafet.common.remote.NetworkResource
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import com.hrudhaykanth116.mafet.todo.data.remote.retrofit.TodoTasksApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRemoteDataSource @Inject constructor(
    private val todoTasksApiService: TodoTasksApiService,
): BaseRemoteDataSource() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTodoTasks(): NetworkResource<List<TodoTask>> = withContext(
        dispatcher
    ){
        val getTodoTasksResponse: Response<List<TodoTask>> = todoTasksApiService.getTodoTasks()
        return@withContext NetworkResource(
            NetworkResource.Status.SUCCESS,
        )
    }

}