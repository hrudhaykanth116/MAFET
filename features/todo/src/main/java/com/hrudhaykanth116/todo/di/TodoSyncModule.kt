package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.todo.data.sync.TodoSyncManager
import com.hrudhaykanth116.todo.domain.sync.ITodoSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoSyncModule {

    @Binds
    @Singleton
    abstract fun bindTodoSyncManager(impl: TodoSyncManager): ITodoSyncManager
}
