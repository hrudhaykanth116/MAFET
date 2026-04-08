package com.hrudhaykanth116.mafet.di

import com.hrudhaykanth116.ai.di.aiModule
import com.hrudhaykanth116.auth.di.authModule
import com.hrudhaykanth116.composeapp.di.composeAppModule
import com.hrudhaykanth116.core.common.di.coreCommonModule
import com.hrudhaykanth116.core.data.di.coreDataModule
import com.hrudhaykanth116.core.network.di.networkModule
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.di.coreUIModule
import com.hrudhaykanth116.journal.di.journalModule
import com.hrudhaykanth116.media.di.mediaModule
import com.hrudhaykanth116.mafet.CrashHandler
import com.hrudhaykanth116.mafet.ads.AdsInitializer
import com.hrudhaykanth116.mafet.update.InAppUpdateManager
import com.hrudhaykanth116.composeapp.RemoteConfigManager
import com.hrudhaykanth116.mafet.main.MainViewModel
import com.hrudhaykanth116.todo.di.todoModule
import com.hrudhaykanth116.tv.di.tvModule
import com.hrudhaykanth116.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(
        coreCommonModule,
        coreDataModule,
        networkModule,
        coreUIModule,
        todoModule,
        weatherModule,
        authModule,
        mediaModule,
        *tvModule.toTypedArray(),
        journalModule,
        aiModule,
        composeAppModule
    )
    single { NetworkMonitor(androidContext()) }
    single { AdsInitializer() }
    single { CrashHandler() }
    single { InAppUpdateManager(androidContext()) }
    single { RemoteConfigManager() }
    viewModel { MainViewModel() }
}
