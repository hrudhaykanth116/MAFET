package com.hrudhaykanth116.weather.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.weather.data.datasources.local.WeatherForeCastLocalDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherMapGeoCodeRemoteDataSourceImpl
import com.hrudhaykanth116.weather.data.datasources.remote.ktor.OpenWeatherApiServiceKtor
import com.hrudhaykanth116.weather.data.repository.GeoCodeRepositoryImpl
import com.hrudhaykanth116.weather.data.repository.WeatherForeCastRepositoryImpl
import com.hrudhaykanth116.weather.domain.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.domain.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCaseFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetWeatherElementIconUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetWeatherIconUseCase
import com.hrudhaykanth116.weather.domain.usecases.ParseCurrentWeatherUseCase
import com.hrudhaykanth116.weather.domain.usecases.ParseDailyForeCastDtoUseCase
import com.hrudhaykanth116.weather.ui.screens.home.WeatherHomeScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformWeatherModule: org.koin.core.module.Module

val weatherModule = module {
    includes(platformWeatherModule)

    single<OpenWeatherApiServiceKtor> {
        OpenWeatherApiServiceKtor(
            httpClient = get<HttpClient>()
        )
    }

    single { WeatherForeCastLocalDataSource() }

    single { WeatherForeCastRemoteDataSource(get()) }

    single<IGeoCodeRemoteDataSource> {
        WeatherMapGeoCodeRemoteDataSourceImpl(get())
    }

    single<IWeatherForeCastRepository> {
        WeatherForeCastRepositoryImpl(
            get(),
            get(named(DispatchersEnum.IoDispatcher))
        )
    }

    single<IGeoCodeRepository> {
        GeoCodeRepositoryImpl(
            get(),
            get(named(DispatchersEnum.IoDispatcher))
        )
    }

    single { GetWeatherIconUseCase() }

    single { GetWeatherElementIconUseCase() }

    single {
        ParseCurrentWeatherUseCase(
            get(),
            get(),
            get()
        )
    }

    single {
        ParseDailyForeCastDtoUseCase(
            get(),
            get(),
            get(),
            get(named(DispatchersEnum.DefaultDispatcher))
        )
    }

    factory {
        GetForeCastFromLatLongUseCase(
            get(),
            get(),
            get()
        )
    }

    factory {
        GetForeCastUseCaseFromLatLongUseCase(
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        WeatherHomeScreenViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}
