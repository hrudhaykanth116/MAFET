package com.hrudhaykanth116.core.ui

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual fun getNetworkMonitor(): NetworkMonitor {
    return object : KoinComponent {}.get<android.content.Context>().let { NetworkMonitor(it) }
}
