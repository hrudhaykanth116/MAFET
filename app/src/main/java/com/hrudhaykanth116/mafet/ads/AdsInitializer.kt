package com.hrudhaykanth116.mafet.ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdsInitializer {

    suspend fun initialize(context: Context) = withContext(Dispatchers.Default) {
        MobileAds.initialize(context)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
        )
    }

    companion object {

        val testDeviceIds = listOf(
            AdRequest.DEVICE_ID_EMULATOR,
            "26E8438EAEF3F76D43C3F740428081A0"
        )

    }

}