package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.todo.data.data_source.local.ITodoLocalDataSource
import com.hrudhaykanth116.todo.data.data_source.local.TodoLocalDataSource
import com.hrudhaykanth116.todo.data.local.room.dao.TodoTasksDao
import com.hrudhaykanth116.todo.data.local.room.dbs.TodoDb
import com.hrudhaykanth116.todo.data.local.room.dbs.getDatabaseBuilder
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
import org.koin.core.qualifier.named
import org.koin.dsl.module

val todoModule = module {
    single<TodoDb> {
        getDatabaseBuilder().build()
    }

    single<TodoTasksDao> { get<TodoDb>().todoTasksDao() }

    single<ITodoLocalDataSource> { TodoLocalDataSource(get()) }

    single<ITodoRepository> {
        TodoRepository(
            get(),
            get(),
            get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }

    single<ITodoSyncManager> {
        TodoSyncManager(
            get(),
            get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }

    factory { ObserveTasksUseCase(get()) }
    factory { GetTaskUseCase(get()) }
    factory { CreateTodoTaskUseCase(get()) }
    factory { UpdateTodoTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }

    factory { com.hrudhaykanth116.todo.ui.mappers.TodoDomainModelMapper(get()) }

    factory {
        TodoListViewModel(
            observeTasksUseCase = get(),
            createTodoTaskUseCase = get(),
            deleteTaskUseCase = get(),
            networkMonitor = get(),
            mapper = get(),
            uniqueIdGenerator = get(),
            dispatcher = get(named(DispatchersEnum.MainDispatcher))
        )
    }

    factory { (todoId: String?) ->
        CreateOrUpdateTodoListViewModel(
            createTodoTaskUseCase = get(),
            getTaskUseCase = get(),
            networkMonitor = get(),
            dateTimeUtils = get(),
            uniqueIdGenerator = get(),
            todoId = todoId
        )
    }
}
