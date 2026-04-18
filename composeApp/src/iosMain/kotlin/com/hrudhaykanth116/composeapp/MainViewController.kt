package com.hrudhaykanth116.composeapp

import androidx.compose.ui.window.ComposeUIViewController
import com.hrudhaykanth116.composeapp.data.RemoteConfigDataSource
import com.hrudhaykanth116.composeapp.di.composeAppModule
import com.hrudhaykanth116.core.common.di.coreCommonModule
import com.hrudhaykanth116.core.data.di.coreDataModule
import com.hrudhaykanth116.core.network.di.networkModule
import com.hrudhaykanth116.core.ui.di.coreUIModule
import com.hrudhaykanth116.journal.di.journalModule
import com.hrudhaykanth116.media.di.mediaModule
import com.hrudhaykanth116.todo.di.todoModule
import com.hrudhaykanth116.tv.di.tvModule
import com.hrudhaykanth116.weather.di.weatherModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController


private fun initKoin() {
    startKoin {
        modules(
            module { single { RemoteConfigDataSource(fetchIntervalSeconds = 0L) } },
            coreCommonModule,
            coreDataModule,
            networkModule,
            coreUIModule,
            todoModule,
            weatherModule,
            mediaModule,
            *tvModule.toTypedArray(),
            journalModule,
            composeAppModule
        )
    }
}

fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController {
        App()
    }
}
