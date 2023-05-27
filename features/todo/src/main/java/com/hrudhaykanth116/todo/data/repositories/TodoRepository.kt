package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.TodoRemoteDataSource
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    // TODO: Follow dependency inversion principle
    private val todoLocalDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
) {

    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTodoTask(): List<TodoTaskDbEntity> {
        return todoLocalDataSource.getTodoTasks()
    }

    suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        return todoLocalDataSource.getTodoTask(id)
    }


    suspend fun createTodoTask(
        id: String,
        title: String,
        description: String,
        category: String,
    ): DataResult<Unit> {

        todoLocalDataSource.createTodoTask(
            TodoTaskDbEntity(
                id = id,
                title = title,
                description = description,
                completed = false,
                category = category
            )
        )

        return DataResult.Success(Unit)


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

}