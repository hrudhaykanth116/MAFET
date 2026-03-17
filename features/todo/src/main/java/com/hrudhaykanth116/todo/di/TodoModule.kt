package com.hrudhaykanth116.todo.di

import android.content.Context
import androidx.room.Room
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.dbs.TodoDb
import com.hrudhaykanth116.todo.data.repositories.TodoRepository
import com.hrudhaykanth116.todo.data.sync.TodoSyncManager
import com.hrudhaykanth116.todo.domain.repository.ITodoRepository
import com.hrudhaykanth116.todo.domain.sync.ITodoSyncManager
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.domain.use_cases.UpdateTodoTaskUseCase
import com.hrudhaykanth116.todo.ui.screens.create.CreateOrUpdateTodoListViewModel
import com.hrudhaykanth116.todo.ui.screens.list.TodoListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val todoModule = module {
    single<TodoDb> {
        Room.databaseBuilder(
            androidContext(),
            TodoDb::class.java,
            TodoDb.TABLE_NAME
        ).apply {
            fallbackToDestructiveMigration()
        }.build()
    }

    single<TodoTasksDao> { get<TodoDb>().todoTasksDao() }

    single<ITodoLocalDataSource> { TodoLocalDataSource(get()) }

    single<ITodoRepository> {
        TodoRepository(
            get(),
            get(),
            get(),
            get(named("IoDispatcher"))
        )
    }

    single<ITodoSyncManager> {
        TodoSyncManager(
            get(),
            get(),
            get(named("IoDispatcher"))
        )
    }

    factory { ObserveTasksUseCase(get()) }
    factory { GetTaskUseCase(get()) }
    factory { CreateTodoTaskUseCase(get()) }
    factory { UpdateTodoTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }

    factory { com.hrudhaykanth116.todo.ui.mappers.TodoDomainModelMapper(get()) }

    viewModel {
        TodoListViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(named("MainDispatcher"))
        )
    }

    viewModel { (todoId: String?) ->
        CreateOrUpdateTodoListViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            todoId
        )
    }
}
