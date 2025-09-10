package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.ITodoRemoteDataSource
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.mappers.toDomain
import com.hrudhaykanth116.todo.data.mappers.toLocal
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoParams
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoLocalDataSource: ITodoLocalDataSource,
    private val remoteDataSource: ITodoRemoteDataSource,
    private val timeProvider: TimeProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): ITodoRepository {

    override suspend fun getTodoTask(): List<TodoModel> = withContext(dispatcher) {
        todoLocalDataSource.getTodoTasks().map { it.toDomain() }
    }

    override fun getTasks(search: String?, category: String?, sort: String): Flow<List<TodoModel>> =
        todoLocalDataSource.getTasks(
            search = search,
            category = category,
            sort = sort
        ).map { list: List<TodoTaskDbEntity> -> list.map { it.toDomain() } }

    override fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoModel>> =
        todoLocalDataSource.getTodoTasksFlow(search, filterCategory, sortItem)
            .map { list: List<TodoTaskDbEntity> -> list.map { it.toDomain() } }

    override suspend fun getTodoTask(id: String): TodoModel? = withContext(dispatcher){
        todoLocalDataSource.getTodoTask(id)?.toDomain()
    }

    override suspend fun createTodoTask(params: CreateTodoParams): DataResult<String> = withContext(dispatcher){

        val idLong: Long = params.id.toLongOrNull() ?: 0L
        when (val remote = remoteDataSource.createTodoTask(
            id = idLong,
            title = params.title,
            description = params.description,
            category = params.category,
            active = true,
        )) {
            is DataResult.Success -> {
                val local = TodoModel(
                    id = params.id,
                    title = params.title,
                    description = params.description,
                    completed = false,
                    category = params.category,
                    priority = params.priority,
                    targetTime = params.targetTime,
                ).toLocal(timeProvider.currentTimeMillis())
                todoLocalDataSource.createTodoTask(local)
                DataResult.Success(params.id)
            }
            is DataResult.Error -> remote
        }
    }

    override suspend fun deleteTasks(taskId: List<String>): Unit = withContext(dispatcher){
        todoLocalDataSource.deleteTasks(taskId)
    }

    override suspend fun deleteAllTasks(): Unit = withContext(dispatcher){
        todoLocalDataSource.deleteAllTasks()
    }

}