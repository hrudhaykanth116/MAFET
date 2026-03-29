package com.hrudhaykanth116.mafet.di

import com.hrudhaykanth116.auth.di.authModule
import com.hrudhaykanth116.core.common.di.coreModule
import com.hrudhaykanth116.core.network.di.networkModule
import com.hrudhaykanth116.journal.di.journalModule
import com.hrudhaykanth116.media.di.mediaModule
import com.hrudhaykanth116.mafet.CrashHandler
import com.hrudhaykanth116.mafet.main.MainViewModel
import com.hrudhaykanth116.todo.di.todoModule
import com.hrudhaykanth116.tv.di.tvModule
import com.hrudhaykanth116.weather.di.weatherModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(
        coreModule,
        networkModule,
        todoModule,
        weatherModule,
        authModule,
        mediaModule,
        *tvModule.toTypedArray(),
        journalModule
    )
    single { CrashHandler() }
    viewModel { MainViewModel(get()) }
}
