package com.hrudhaykanth116.weather.data.di

import com.hrudhaykanth116.weather.data.datasources.local.WeatherForeCastLocalDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherMapGeoCodeRemoteDataSourceImpl
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.WeatherMapGeoCodeApiService
import com.hrudhaykanth116.weather.data.repository.GeoCodeRepositoryImpl
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.data.repository.WeatherForeCastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideForeCastRepository(
        remoteDataSource: WeatherForeCastRemoteDataSource,
        localDataSource: WeatherForeCastLocalDataSource,
    ): IWeatherForeCastRepository = WeatherForeCastRepositoryImpl(
        remoteDataSource,
        localDataSource
    )

    @Provides
    fun provideGeoCodingRemoteDataSource(
        weatherMapGeoCodeApiService: WeatherMapGeoCodeApiService
    ): IGeoCodeRemoteDataSource = WeatherMapGeoCodeRemoteDataSourceImpl(
        weatherMapGeoCodeApiService
    )

    @Provides
    fun provideGeoCodingRepository(
        geoCodeRemoteDataSource: IGeoCodeRemoteDataSource
    ): IGeoCodeRepository = GeoCodeRepositoryImpl(
        geoCodeRemoteDataSource
    )

}