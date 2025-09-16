package com.hrudhaykanth116.todo.data.repositories

import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.ITodoRemoteDataSource
import com.hrudhaykanth116.todo.domain.model.TodoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull

@OptIn(ExperimentalCoroutinesApi::class)
class TodoRepositoryTest {

    private val dispatcher = StandardTestDispatcher()

    @Test
    fun createTodoTask_success_persistsLocally() = runTest(dispatcher) {
        val localDataSource = mock(ITodoLocalDataSource::class.java)
        val remoteDataSource = mock(ITodoRemoteDataSource::class.java)
        val timeProvider = mock(TimeProvider::class.java)

        val params = TodoModel(
            id = "1",
            title = "T",
            description = "D",
            category = "C",
            priority = 1,
            targetTime = null,
        )

        `when`(
            remoteDataSource.createTodoTask(
                anyLong(), anyString(), any(), anyString(), anyBoolean()
            )
        ).thenReturn(
            ApiResultWrapper.Success(
                com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse(
                    200,
                    "ok",
                    com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse.TodoData(id = 1L)
                )
            )
        )

        `when`(timeProvider.currentTimeMillis()).thenReturn(999L)

        val repo = TodoRepository(localDataSource, remoteDataSource, timeProvider, dispatcher)
        val result = repo.createTodoTask(params, "123")
        assertTrue(result is RepoResultWrapper.Success)

        verify(localDataSource).createTodoTask(anyOrNull())
        verify(timeProvider).currentTimeMillis()
    }
}