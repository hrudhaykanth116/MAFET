package com.hrudhaykanth116.todo.data.data_source.local

import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoLocalDataSource @Inject constructor(
    private val todoTasksDao: TodoTasksDao,
): ITodoLocalDataSource {

    override suspend fun getTodoTasks(): List<TodoTaskDbEntity> {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTasks()
    }

    override fun getTasks(
        search: String?, category: String?, sort: String
    ) = todoTasksDao.getTasks(
        search = search,
        category = category,
        sort = sort
    )

    override fun getTodoTasksFlow(
        search: String,
        filterCategory: String?,
        sortItem: String,
    ): Flow<List<TodoTaskDbEntity>> {
        // hrudhay_check_list: Revisit this
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

    override suspend fun getTodoTask(id: String): TodoTaskDbEntity? {
        // Since room runs on io dispatcher already, no need to switch dispatcher.
        return todoTasksDao.getTaskById(id)
    }

    override suspend fun createTodoTask(todoTaskDbEntity: TodoTaskDbEntity) {
        todoTasksDao.insertOrUpdate(todoTaskDbEntity)
    }

    override suspend fun deleteTasks(taskId: List<String>) {
        todoTasksDao.deleteTasksByIds(taskId)
    }

    override suspend fun deleteAllTasks() {
        todoTasksDao.deleteTasks()
    }

}