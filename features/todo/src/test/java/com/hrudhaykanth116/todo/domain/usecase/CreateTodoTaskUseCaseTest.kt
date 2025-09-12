package com.hrudhaykanth116.todo.domain.usecase

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
