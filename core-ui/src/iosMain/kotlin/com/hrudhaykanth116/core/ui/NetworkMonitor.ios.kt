package com.hrudhaykanth116.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_monitor_t
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

actual class NetworkMonitor {

    private val _internetAvailabilityStateFlow = MutableStateFlow(true)
    private var pathMonitor: nw_path_monitor_t? = null

    actual val internetAvailabilityStateFlow: StateFlow<Boolean>
        get() = _internetAvailabilityStateFlow.asStateFlow()

    actual fun registerNetworkCallback() {
        pathMonitor = nw_path_monitor_create()
        pathMonitor?.let { monitor ->
            nw_path_monitor_set_update_handler(monitor) { path ->
                val status = nw_path_get_status(path)
                val isConnected = status == nw_path_status_satisfied
                _internetAvailabilityStateFlow.value = isConnected
            }
            nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
            nw_path_monitor_start(monitor)
        }
    }

    actual fun isNetworkAvailable(): Boolean {
        return _internetAvailabilityStateFlow.value
    }

    actual fun triggerNetworkCheck() {
        // NWPathMonitor automatically triggers updates, no manual check needed
    }

    fun stopMonitoring() {
        pathMonitor?.let { nw_path_monitor_cancel(it) }
        pathMonitor = null
    }
}
