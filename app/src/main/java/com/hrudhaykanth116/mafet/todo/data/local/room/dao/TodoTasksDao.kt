package com.hrudhaykanth116.mafet.todo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTasksDao {

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM TodoTask")
    fun observeTasks(): Flow<List<TodoTask>>

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM TodoTask WHERE id = :taskId")
    fun observeTaskById(taskId: String): Flow<TodoTask>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM TodoTask")
    suspend fun getTasks(): List<TodoTask>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM TodoTask WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TodoTask?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoTask)

    /**
     * Update a task.
     *
     * @param task task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    suspend fun updateTask(task: TodoTask): Int

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE TodoTask SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM TodoTask WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM TodoTask")
    suspend fun deleteTasks()

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM TodoTask WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int
}