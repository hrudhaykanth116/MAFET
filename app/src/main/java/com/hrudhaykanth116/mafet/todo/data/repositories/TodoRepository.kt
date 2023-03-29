package com.hrudhaykanth116.mafet.todo.data.repositories

import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
import com.hrudhaykanth116.mafet.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.mafet.todo.data.data_source.remote.TodoRemoteDataSource
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.mafet.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.mafet.todo.domain.model.TaskCategory
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

    suspend fun createTodoTask(
        id: Long,
        title: String,
        description: String?,
        category: TaskCategory,
        active: Boolean
    ): DataResult<Unit> {
        val response: DataResult<PostTodoResponse> = remoteDataSource.createTodoTask(
            id, title, description, category, active
        )
        return when (response) {
            is DataResult.Success -> {
                val data: PostTodoResponse.TodoData = response.data.data
                val todoTaskDbEntity = data.toDbEntity()
                todoLocalDataSource.createTodoTask(
                    todoTaskDbEntity
                )
                DataResult.Success(Unit)
            }
            is DataResult.Error -> {
                DataResult.Error(
                    uiMessage = UIText.Text("Api error failure.")
                )
            }
        }
    }

}