package com.hrudhaykanth116.weather.di

import android.content.Context
import com.hrudhaykanth116.weather.data.datasources.local.WeatherForeCastLocalDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherMapGeoCodeRemoteDataSourceImpl
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import com.hrudhaykanth116.weather.data.repository.GeoCodeRepositoryImpl
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.data.repository.WeatherForeCastRepositoryImpl
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetForeCastUseCaseFromLatLongUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetReverseGeoCodingUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetWeatherElementIconUseCase
import com.hrudhaykanth116.weather.domain.usecases.GetWeatherIconUseCase
import com.hrudhaykanth116.weather.domain.usecases.ParseCurrentWeatherUseCase
import com.hrudhaykanth116.weather.domain.usecases.ParseDailyForeCastDtoUseCase
import com.hrudhaykanth116.weather.ui.screens.home.WeatherHomeScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val weatherModule = module {
    // Network - Retrofit & API Service
    single(named("weather_baseurl")) { "https://api.openweathermap.org/" }

    single(named("weather_retrofit")) {
        get<Retrofit.Builder>()
            .baseUrl(get<String>(named("weather_baseurl")))
            .build()
    }

    single<OpenWeatherApiService> {
        get<Retrofit>(named("weather_retrofit")).create(OpenWeatherApiService::class.java)
    }

    // Data Sources
    single { WeatherForeCastLocalDataSource() }

    single { WeatherForeCastRemoteDataSource(get()) }

    single<IGeoCodeRemoteDataSource> {
        WeatherMapGeoCodeRemoteDataSourceImpl(get())
    }

    // Repositories
    single<IWeatherForeCastRepository> {
        WeatherForeCastRepositoryImpl(
            get(),
            get(named("IoDispatcher"))
        )
    }

    single<IGeoCodeRepository> {
        GeoCodeRepositoryImpl(get())
    }

    // Use Cases
    single { GetWeatherIconUseCase() }

    single { GetWeatherElementIconUseCase() }

    single { GetReverseGeoCodingUseCase(get()) }

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
            get()
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

    // ViewModels
    viewModel {
        WeatherHomeScreenViewModel(
            get(),
            get(),
            androidContext(),
            get()
        )
    }
}
