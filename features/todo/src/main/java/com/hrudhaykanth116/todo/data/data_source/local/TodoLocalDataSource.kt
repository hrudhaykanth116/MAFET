package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoLocalDataSource @Inject constructor(
    private val todoTasksDao: TodoTasksDao,
) {

    suspend fun getTodoTasks(): List<TodoTaskDbEntity> {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTasks()
    }

    fun getTasks(
        search: String?, category: String?, sort: String
    ) = todoTasksDao.getTasks(
        search = search,
        category = category,
        sort = sort
    )

    fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoTaskDbEntity>> {
        // TODO: Revisit this
        return if(filterCategory.isNullOrEmpty()){
            todoTasksDao.getTasksFlow()
        }else{
            todoTasksDao.getFilteredTasksFlow(
                // search,
                filterCategory,
                sortItem
            )
        }

    }

    suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTaskById(id)
    }

    suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        todoTasksDao.insertOrUpdate(todoTaskDbEntity)
    }

    suspend fun deleteTasks(taskId: List<String>) {
        todoTasksDao.deleteTasksByIds(taskId)
    }

    suspend fun deleteAllTasks() {
        todoTasksDao.deleteTasks()
    }

}