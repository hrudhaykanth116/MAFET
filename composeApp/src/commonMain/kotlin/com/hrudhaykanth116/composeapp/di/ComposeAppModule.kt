package com.hrudhaykanth116.composeapp.di

import com.hrudhaykanth116.composeapp.AppViewModel
import com.hrudhaykanth116.composeapp.data.RemoteConfigRepository
import com.hrudhaykanth116.composeapp.domain.GetRemoteConfigUseCase
import com.hrudhaykanth116.composeapp.domain.IRemoteConfigRepository
import com.hrudhaykanth116.composeapp.home.dashboard.DashboardViewModel
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardJournalUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTodoUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTvUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardWeatherUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val composeAppModule = module {

    factory { GetDashboardTodoUseCase(get()) }
    factory { GetDashboardWeatherUseCase(get(), get()) }
    factory { GetDashboardTvUseCase(get()) }
    factory { GetDashboardJournalUseCase(get(), get()) }

    single<IRemoteConfigRepository> { RemoteConfigRepository(get()) }
    factory { GetRemoteConfigUseCase(get()) }

    viewModel {
        AppViewModel(getRemoteConfig = get(), networkMonitor = get())
    }

    viewModel {
        DashboardViewModel(
            getDashboardTodoUseCase = get(),
            getDashboardWeatherUseCase = get(),
            getDashboardTvUseCase = get(),
            getDashboardJournalUseCase = get(),
            networkMonitor = get()
        )
    }
}
