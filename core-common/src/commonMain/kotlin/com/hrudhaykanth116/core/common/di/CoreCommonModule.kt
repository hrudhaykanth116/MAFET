package com.hrudhaykanth116.core.common.di

import com.hrudhaykanth116.core.common.time.SystemTimeProvider
import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import org.koin.dsl.module

val coreCommonModule = module {
    includes(dispatchersModule)

    single { UniqueIdGenerator() }
    single { DateTimeUtils() }
    single { TemperatureConverter() }
    single<TimeProvider> { SystemTimeProvider() }
}
