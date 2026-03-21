package com.hrudhaykanth116.core.common.di

import com.hrudhaykanth116.core.ads.AdsInitializer
import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.common.di.dispatchersModule
import com.hrudhaykanth116.core.ui.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    includes(
        dispatchersModule,
        timeModule
    )
    single { AdsInitializer() }
    single { NetworkMonitor(androidContext()) }
    single { UniqueIdGenerator() }
    single { DateTimeUtils() }
    single { TemperatureConverter() }
}
