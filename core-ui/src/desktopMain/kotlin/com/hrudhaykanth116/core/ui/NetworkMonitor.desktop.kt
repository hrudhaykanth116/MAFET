package com.hrudhaykanth116.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Desktop implementation of NetworkMonitor.
 * Currently assumes network is always available.
 * TODO: Implement actual network monitoring for desktop using java.net or ktor.
 */
actual class NetworkMonitor {

    private val _internetAvailabilityStateFlow = MutableStateFlow(true)

    actual val internetAvailabilityStateFlow: StateFlow<Boolean>
        get() = _internetAvailabilityStateFlow.asStateFlow()

    actual fun registerNetworkCallback() {
        // No-op on desktop for now
        println("NetworkMonitor: registerNetworkCallback called (desktop)")
    }

    actual fun isNetworkAvailable(): Boolean {
        // Assume network is available on desktop
        // TODO: Implement actual network check using java.net.InetAddress or similar
        return true
    }

    actual fun triggerNetworkCheck() {
        // Assume network is available
        _internetAvailabilityStateFlow.value = true
    }
}
