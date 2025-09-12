package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.ITodoRemoteDataSource
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.todo.data.mappers.toDomain
import com.hrudhaykanth116.todo.data.mappers.toLocal
import com.hrudhaykanth116.todo.domain.model.TodoModel
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
) : ITodoRepository {

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

    override suspend fun getTodoTask(id: String): RepoResultWrapper<TodoModel> =
        withContext(dispatcher) {
            val todoEntity = todoLocalDataSource.getTodoTask(id)

            if (todoEntity == null) {
                return@withContext RepoResultWrapper.Error(ErrorState.NotFound)
            } else {
                return@withContext RepoResultWrapper.Success(todoEntity.toDomain())
            }
        }

    override suspend fun createTodoTask(todoModel: TodoModel, id: String): RepoResultWrapper<Unit> =
        withContext(dispatcher) {
            val local = todoModel.toLocal(timeProvider.currentTimeMillis(), id)
            todoLocalDataSource.createTodoTask(local)
            RepoResultWrapper.Success(Unit)
        }


    override suspend fun deleteTasks(taskId: List<String>): Unit = withContext(dispatcher) {
        todoLocalDataSource.deleteTasks(taskId)
    }

    override suspend fun deleteAllTasks(): Unit = withContext(dispatcher) {
        todoLocalDataSource.deleteAllTasks()
    }

}