package com.hrudhaykanth116.core.ui.di

import com.hrudhaykanth116.core.ui.getNetworkMonitor
import com.hrudhaykanth116.core.ui.notification.getNotificationScheduler
import org.koin.dsl.module

val coreUIModule = module {
    single { getNetworkMonitor() }
    single { getNotificationScheduler() }
}
