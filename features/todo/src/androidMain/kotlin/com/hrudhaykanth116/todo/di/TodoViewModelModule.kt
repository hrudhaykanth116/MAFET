package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.todo.ui.screens.create.CreateOrUpdateTodoListViewModel
import com.hrudhaykanth116.todo.ui.screens.list.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Android-specific module for ViewModel declarations.
 * Uses org.koin.androidx.viewmodel which is Android-only.
 */
val todoViewModelModule = module {
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
