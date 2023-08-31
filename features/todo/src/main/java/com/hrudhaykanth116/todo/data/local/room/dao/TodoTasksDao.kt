package com.hrudhaykanth116.todo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface TodoTasksDao : com.hrudhaykanth116.core.data.local.db.BaseDao<TodoTaskDbEntity> {

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM TodoTaskDbEntity")
    fun observeTasks(): Flow<List<TodoTaskDbEntity>>

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM TodoTaskDbEntity WHERE id = :taskId")
    fun observeTaskById(taskId: String): Flow<TodoTaskDbEntity>

    fun observeDistinctTaskById(taskId: String): Flow<TodoTaskDbEntity> =
        observeTaskById(taskId).distinctUntilChanged()

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM TodoTaskDbEntity")
    suspend fun getTasks(): List<TodoTaskDbEntity>

    @Query("SELECT * FROM TodoTaskDbEntity")
    fun getTasksFlow(): Flow<List<TodoTaskDbEntity>>

    @Query("SELECT * FROM TodoTaskDbEntity WHERE category = :filterCategory")
    fun getFilteredTasksFlow(
        // search: String,
        filterCategory: String,
    ): Flow<List<TodoTaskDbEntity>>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM TodoTaskDbEntity WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TodoTaskDbEntity?

    // /**
    //  * Insert a task in the database. If the task already exists, replace it.
    //  *
    //  * @param task the task to be inserted.
    //  */
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend fun insertTask(task: TodoTaskDbEntity)
    //
    // /**
    //  * Update a task.
    //  *
    //  * @param task task to be updated
    //  * @return the number of tasks updated. This should always be 1.
    //  */
    // @Update
    // suspend fun updateTask(task: TodoTaskDbEntity): Int

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE TodoTaskDbEntity SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM TodoTaskDbEntity WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM TodoTaskDbEntity")
    suspend fun deleteTasks()

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM TodoTaskDbEntity WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int
}