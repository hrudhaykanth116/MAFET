package com.hrudhaykanth116.todo.di

import android.content.Context
import androidx.room.Room
import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.dbs.TodoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): TodoDb {
        return Room.databaseBuilder(
            appContext, TodoDb::class.java,
            TodoDb.TABLE_NAME
        ).apply {
            fallbackToDestructiveMigration()
            allowMainThreadQueries()
        }.build()
    }

    @Singleton
    @Provides
    fun providesCareGiversChatDao(todoDb: TodoDb): TodoTasksDao =
        todoDb.todoTasksDao()

}