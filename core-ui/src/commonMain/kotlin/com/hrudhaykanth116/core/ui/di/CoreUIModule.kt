package com.hrudhaykanth116.core.ui.di

import com.hrudhaykanth116.core.ui.getNetworkMonitor
import org.koin.dsl.module

val coreUIModule = module {
    single { getNetworkMonitor() }
}
