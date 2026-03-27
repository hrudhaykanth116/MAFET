package com.hrudhaykanth116.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class NetworkMonitor {

    private val _internetAvailabilityStateFlow = MutableStateFlow(true)

    actual val internetAvailabilityStateFlow: StateFlow<Boolean>
        get() = _internetAvailabilityStateFlow.asStateFlow()

    actual fun registerNetworkCallback() {
    }

    actual fun isNetworkAvailable(): Boolean {
        return true
    }

    actual fun triggerNetworkCheck() {
        _internetAvailabilityStateFlow.value = true
    }
}
