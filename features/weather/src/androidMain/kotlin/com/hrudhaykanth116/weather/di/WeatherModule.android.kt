package com.hrudhaykanth116.weather.di

import com.hrudhaykanth116.weather.location.LocationService
import com.hrudhaykanth116.weather.location.LocationServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformWeatherModule = module {
    single<LocationService> {
        LocationServiceImpl(androidContext())
    }
}
