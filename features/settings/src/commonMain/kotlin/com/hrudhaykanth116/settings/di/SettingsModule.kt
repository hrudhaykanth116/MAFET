package com.hrudhaykanth116.settings.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.settings.data.repository.SettingsRepositoryImpl
import com.hrudhaykanth116.settings.domain.repository.ISettingsRepository
import com.hrudhaykanth116.settings.domain.use_cases.GetThemeUseCase
import com.hrudhaykanth116.settings.domain.use_cases.SetThemeUseCase
import com.hrudhaykanth116.settings.ui.screens.SettingsViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsModule = module {
    single<ISettingsRepository> {
        SettingsRepositoryImpl(userPreferencesRepository = get())
    }

    factory { GetThemeUseCase(repository = get()) }
    factory { SetThemeUseCase(repository = get()) }

    factory {
        SettingsViewModel(
            getThemeUseCase = get(),
            setThemeUseCase = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.MainDispatcher))
        )
    }
}
