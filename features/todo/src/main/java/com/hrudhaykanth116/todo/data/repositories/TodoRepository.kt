package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.TodoRemoteDataSource
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    // TODO: Follow dependency inversion principle
    private val todoLocalDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getTodoTask(): List<TodoTaskDbEntity> {
        return todoLocalDataSource.getTodoTasks()
    }

    fun getTasks(search: String?, category: String?, sort: String) = todoLocalDataSource.getTasks(
        search = search,
        category = category,
        sort = sort
    )

    fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ) = todoLocalDataSource.getTodoTasksFlow(search, filterCategory, sortItem)

    suspend fun getTodoTask(id: String): TodoTaskDbEntity? = withContext(dispatcher){
        todoLocalDataSource.getTodoTask(id)
    }


    suspend fun createTodoTask(
        id: String,
        title: String,
        description: String,
        category: String,
        priority: Int,
        targetTime: Long? = null,
    ): DataResult<Unit> = withContext(dispatcher){

        todoLocalDataSource.createTodoTask(
            TodoTaskDbEntity(
                id = id,
                title = title,
                description = description,
                completed = false,
                category = category,
                priority = priority,
                targetTime = targetTime,
                timeUpdated = System.currentTimeMillis()
            )
        )

        DataResult.Success(Unit)


        // val response: com.hrudhaykanth116.core.data.models.DataResult<PostTodoResponse> = remoteDataSource.createTodoTask(
        //     id, title, description, category, active
        // )
        // return when (response) {
        //     is com.hrudhaykanth116.core.data.models.DataResult.Success -> {
        //         val data: PostTodoResponse.TodoData = response.data.data
        //         val todoTaskDbEntity = data.toDbEntity()
        //         todoLocalDataSource.createTodoTask(
        //             todoTaskDbEntity
        //         )
        //         com.hrudhaykanth116.core.data.models.DataResult.Success(Unit)
        //     }
        //     is com.hrudhaykanth116.core.data.models.DataResult.Error -> {
        //         com.hrudhaykanth116.core.data.models.DataResult.Error(
        //             uiMessage = com.hrudhaykanth116.core.data.models.UIText.Text("Api error failure.")
        //         )
        //     }
        // }
    }

    suspend fun deleteTasks(taskId: List<String>) = withContext(dispatcher){
        todoLocalDataSource.deleteTasks(taskId)
    }

    suspend fun deleteAllTasks() = withContext(dispatcher){
        todoLocalDataSource.deleteAllTasks()
    }

}