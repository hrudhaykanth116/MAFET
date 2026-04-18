package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.tv.data.repositories.tv.TvPagingRepository
import com.hrudhaykanth116.tv.ui.screens.TvShowsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val tvPlatformModule: Module = module {
    single { TvPagingRepository(get()) }

    factory { TvShowsViewModel(get(), get(), get()) }
}
