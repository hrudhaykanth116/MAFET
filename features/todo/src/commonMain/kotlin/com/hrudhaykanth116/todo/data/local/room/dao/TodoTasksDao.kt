package com.hrudhaykanth116.todo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.hrudhaykanth116.core.data.local.room.BaseDao
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface TodoTasksDao : BaseDao<TodoTaskDbEntity> {

    @Query("SELECT * FROM TodoTaskDbEntity")
    fun observeTasks(): Flow<List<TodoTaskDbEntity>>

    @Query("SELECT * FROM TodoTaskDbEntity WHERE id = :taskId")
    fun observeTaskById(taskId: String): Flow<TodoTaskDbEntity>

    fun observeDistinctTaskById(taskId: String): Flow<TodoTaskDbEntity> =
        observeTaskById(taskId).distinctUntilChanged()

    @Query("SELECT * FROM TodoTaskDbEntity")
    suspend fun getTasks(): List<TodoTaskDbEntity>

    @Query("SELECT * FROM TodoTaskDbEntity")
    fun getTasksFlow(): Flow<List<TodoTaskDbEntity>>

    @Query(
        """
            SELECT * FROM TodoTaskDbEntity
            WHERE category = :filterCategory
                ORDER BY
                    CASE WHEN :sortItem = 'priority' THEN priority END DESC,
                    CASE WHEN :sortItem = 'targetTime' THEN targetTime END DESC
        """
    )
    fun getFilteredTasksFlow(
        filterCategory: String,
        sortItem: String
    ): Flow<List<TodoTaskDbEntity>>

    @Query("""
        SELECT * FROM TodoTaskDbEntity
        WHERE (:search IS NULL OR title LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%')
        AND (:category IS NULL OR category = :category)
        ORDER BY 
            CASE WHEN :sort = 'priority' THEN priority END DESC,
            CASE WHEN :sort = 'targetTime' THEN targetTime IS NULL END ASC,
            CASE WHEN :sort = 'targetTime' THEN targetTime END ASC
    """)
    fun getTasks(search: String?, category: String?, sort: String): Flow<List<TodoTaskDbEntity>>

    @Query("SELECT * FROM TodoTaskDbEntity WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TodoTaskDbEntity?

    @Query("UPDATE TodoTaskDbEntity SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM TodoTaskDbEntity WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    @Query("DELETE FROM TodoTaskDbEntity WHERE id IN (:taskId)")
    suspend fun deleteTasksByIds(taskId: List<String>): Int

    @Query("DELETE FROM TodoTaskDbEntity")
    suspend fun deleteTasks()

    @Query("DELETE FROM TodoTaskDbEntity WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int

    @Query("SELECT * FROM TodoTaskDbEntity WHERE syncStatus != 'synced'")
    suspend fun getPendingTasks(): List<TodoTaskDbEntity>

    @Query("SELECT COUNT(*) FROM TodoTaskDbEntity WHERE syncStatus != 'synced'")
    fun observePendingCount(): Flow<Int>

    @Query("UPDATE TodoTaskDbEntity SET syncStatus = :status WHERE id = :taskId")
    suspend fun updateSyncStatus(taskId: String, status: String)

    @Query("UPDATE TodoTaskDbEntity SET syncStatus = 'pending_delete' WHERE id IN (:taskIds)")
    suspend fun markForDeletion(taskIds: List<String>)

    @Query("DELETE FROM TodoTaskDbEntity WHERE id IN (:taskIds) AND syncStatus = 'synced'")
    suspend fun deleteSyncedTasks(taskIds: List<String>)
}