package com.hrudhaykanth116.core.ui

import kotlinx.coroutines.flow.StateFlow

/**
 * Expect declaration for network monitoring.
 * Platform-specific implementations handle network connectivity.
 */
expect class NetworkMonitor {
    val internetAvailabilityStateFlow: StateFlow<Boolean>

    fun registerNetworkCallback()
    fun isNetworkAvailable(): Boolean
    fun triggerNetworkCheck()
}
