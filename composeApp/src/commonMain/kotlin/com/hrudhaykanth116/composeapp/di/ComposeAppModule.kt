package com.hrudhaykanth116.composeapp.di

import com.hrudhaykanth116.composeapp.home.dashboard.DashboardViewModel
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTodoUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTvUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardWeatherUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val composeAppModule = module {

    factory { GetDashboardTodoUseCase(get()) }
    factory { GetDashboardWeatherUseCase(get(), get()) }
    factory { GetDashboardTvUseCase(get()) }

    viewModel {
        DashboardViewModel(
            getDashboardTodoUseCase = get(),
            getDashboardWeatherUseCase = get(),
            getDashboardTvUseCase = get(),
            networkMonitor = get()
        )
    }
}
