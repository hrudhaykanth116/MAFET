package com.hrudhaykanth116.core.common.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("Use Network monitor")
class OnlineTracker {

    companion object{
        val isOnline = true
    }

}

@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _internetAvailabilityStateFlow = MutableStateFlow<Boolean>(
        // connectivityManager.getActiveNetworkInfo()?.isConnected() ?: false
        isNetworkAvailable()
    )

    val internetAvailabilityStateFlow
        get() = _internetAvailabilityStateFlow.asStateFlow()

    fun registerNetworkCallback() {
        Log.d(TAG, "registerNetworkCallback: ")


        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "onAvailable: ")
                onInternetAvailable()
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "onLost: ")
                onInternetLost()
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Log.d(TAG, "onUnavailable: ")
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                super.onBlockedStatusChanged(network, blocked)
                Log.d(TAG, "onBlockedStatusChanged: blocked: $blocked")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val isNetworkConnected =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (isNetworkConnected) {
                    onInternetAvailable()
                } else {
                    onInternetLost()
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                Log.d(TAG, "onLosing: ")
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties)
                Log.d(TAG, "onLinkPropertiesChanged: ")
            }

        }

        // will be unregistered when app exists.
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun onInternetLost() {
        Log.d(TAG, "onInternetLost: ")
        _internetAvailabilityStateFlow.value = false
    }

    private fun onInternetAvailable() {
        Log.d(TAG, "onInternetAvailable: ")
        // ServerLogger.getInstance().queueMsg(
        //     RummyApplication.getAppContext(), "Lobby",
        //     "getRound() - round lookup failed for roundUniqueId: $roundUniqueId"
        // )

        _internetAvailabilityStateFlow.value = true
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isNetworkAvailable = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val cap = cm.getNetworkCapabilities(cm.activeNetwork)
            isNetworkAvailable =  cap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networks = cm.allNetworks
            for (n in networks) {
                val nInfo = cm.getNetworkInfo(n)
                if (nInfo != null && nInfo.isConnected) isNetworkAvailable = true
            }
        } else {
            val networks = cm.allNetworkInfo
            for (nInfo in networks) {
                if (nInfo != null && nInfo.isConnected) isNetworkAvailable = true
            }
        }

        Log.d(TAG, "isNetworkAvailable: $isNetworkAvailable")
        return isNetworkAvailable
    }

    fun triggerNetworkCheck() {
        Log.d(TAG, "triggerNetworkCheck: ")
        if (isNetworkAvailable()) {
            onInternetAvailable()
        }else{
            onInternetLost()
        }
    }

    companion object {
        private const val TAG = "NetworkMonitor"
    }

}