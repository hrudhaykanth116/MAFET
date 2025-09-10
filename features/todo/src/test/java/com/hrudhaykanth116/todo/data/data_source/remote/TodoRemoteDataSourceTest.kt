package com.hrudhaykanth116.todo.data.data_source.remote

import com.hrudhaykanth116.core.data.models.DataResult
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TodoRemoteDataSourceTest {

    @Test
    fun getTodoTasks_success() {
        val ds = TodoRemoteDataSource(
            FakeApiService(true),
            dispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )
        val result = kotlinx.coroutines.runBlocking { ds.getTodoTasks() }
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun createTodoTask_error() {
        val ds = TodoRemoteDataSource(
            FakeApiService(false),
            dispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )
        val result = kotlinx.coroutines.runBlocking {
            ds.createTodoTask(
                id = 1L,
                title = "t",
                description = null,
                category = "General",
                active = true,
            )
        }
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun createTodoTask_success() {
        val ds = TodoRemoteDataSource(
            FakeApiService(true),
            dispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )
        val result = kotlinx.coroutines.runBlocking {
            ds.createTodoTask(
                id = 2L,
                title = "new task",
                description = "desc",
                category = "General",
                active = true,
            )
        }
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun getTodoTasks_error() = runTest {
        val ds = FakeApiService(false)
        val response = ds.getTodoTasks()
        val result = kotlinx.coroutines.runBlocking { ds.getTodoTasks() }
    }

}
