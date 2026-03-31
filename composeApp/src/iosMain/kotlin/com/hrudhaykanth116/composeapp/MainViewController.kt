package com.hrudhaykanth116.composeapp

import androidx.compose.ui.window.ComposeUIViewController
import com.hrudhaykanth116.composeapp.di.composeAppModule
import com.hrudhaykanth116.core.common.di.coreCommonModule
import com.hrudhaykanth116.core.network.di.networkModule
import com.hrudhaykanth116.core.ui.di.coreUIModule
import com.hrudhaykanth116.media.di.mediaModule
import com.hrudhaykanth116.todo.di.todoModule
import com.hrudhaykanth116.tv.di.tvModule
import com.hrudhaykanth116.weather.di.weatherModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController


private fun initKoin() {
    startKoin {
        modules(coreCommonModule, networkModule, coreUIModule, todoModule, weatherModule, mediaModule, *tvModule.toTypedArray(), composeAppModule)
    }
}

fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController { App() }
}
