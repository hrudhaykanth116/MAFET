package com.hrudhaykanth116.todo.domain.usecase

import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.ITodoRemoteDataSource
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoParams
import com.hrudhaykanth116.todo.data.local.room.tables.TodoTaskDbEntity
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.todo.data.remote.models.PostTodoResponse
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class CreateTodoTaskUseCaseTest {
    // @Test
    // fun `createTodoTask returns success and persists locally on remote success`() = runTest {
    //     val local = mock(ITodoLocalDataSource::class.java)
    //     val remote = mock(ITodoRemoteDataSource::class.java)
    //     val params = CreateTodoParams(
    //         id = "1",
    //         title = "T",
    //         description = "D",
    //         category = "C",
    //         priority = 1,
    //         targetTime = null,
    //     )
    //     `when`(remote.createTodoTask(anyLong(), anyString(), any(), anyString(), anyBoolean()))
    //         .thenReturn(DataResult.Success(PostTodoResponse()))
    //
    //     val useCase = CreateTodoTaskUseCase(local, remote)
    //     val result: DomainState<CreateOrUpdateTodoDomainModel> = useCase.invoke(params)
    //
    //     assertTrue(result is DataResult.Success)
    //     verify(local).createTodoTask(any())
    //     verify(remote).createTodoTask(anyLong(), anyString(), any(), anyString(), anyBoolean())
    // }
}
