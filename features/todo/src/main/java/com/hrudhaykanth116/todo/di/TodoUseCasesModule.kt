package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoUseCasesModule {

    @Provides
    @Singleton
    fun provideObserveTasksUseCase(repo: ITodoRepository): ObserveTasksUseCase =
        ObserveTasksUseCase(repo)

    @Provides
    @Singleton
    fun provideGetTaskUseCase(repo: ITodoRepository): GetTaskUseCase =
        GetTaskUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateTodoTaskUseCase(
        repo: ITodoRepository,
    ): CreateTodoTaskUseCase = CreateTodoTaskUseCase(repo)
} 