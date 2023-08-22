package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoLocalDataSource @Inject constructor(
    private val todoTasksDao: TodoTasksDao,
) {

    // Use dispatcher only if the calls need to be run on io thread.
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTodoTasks(): List<TodoTaskDbEntity> {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTasks()
    }

    fun getTodoTasksFlow() = todoTasksDao.getTasksFlow()

    suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTaskById(id)
    }

    suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity){
        todoTasksDao.insertOrUpdate(todoTaskDbEntity)
    }

    suspend fun deleteTask(taskId: String) {
        todoTasksDao.deleteTaskById(taskId)
    }

}