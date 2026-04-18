package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.tv.data.repositories.tv.TvListRepository
import com.hrudhaykanth116.tv.ui.screens.TvShowsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// TODO: Desktop  pagination should be implemented common
actual val tvPlatformModule: Module = module {
    single { TvListRepository(get()) }

    factory { TvShowsViewModel(get(), get(), get()) }
}
