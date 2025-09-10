package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.ITodoRemoteDataSource
import com.hrudhaykanth116.todo.data.data_source.remote.TodoRemoteDataSource
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTodoRepository(impl: TodoRepository): ITodoRepository

    @Binds
    @Singleton
    abstract fun bindTodoLocalDataSource(impl: TodoLocalDataSource): ITodoLocalDataSource

    @Binds
    @Singleton
    abstract fun bindTodoRemoteDataSource(impl: TodoRemoteDataSource): ITodoRemoteDataSource
} 