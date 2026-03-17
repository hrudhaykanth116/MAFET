package com.hrudhaykanth116.core.common.di

import com.hrudhaykanth116.core.common.time.SystemTimeProvider
import com.hrudhaykanth116.core.common.time.TimeProvider
import org.koin.dsl.module

val timeModule = module {
    single<TimeProvider> { SystemTimeProvider() }
} 