package com.hrudhaykanth116.composeapp

import androidx.compose.ui.window.ComposeUIViewController
import com.hrudhaykanth116.core.common.di.coreCommonModule
import com.hrudhaykanth116.core.ui.di.coreUIModule
import com.hrudhaykanth116.todo.di.todoModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController


private fun initKoin() {
    startKoin {
        modules(coreCommonModule, coreUIModule, todoModule)
    }
}

fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController { App() }
}
