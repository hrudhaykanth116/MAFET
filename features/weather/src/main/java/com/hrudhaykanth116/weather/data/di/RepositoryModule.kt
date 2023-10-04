package com.hrudhaykanth116.weather.data.di

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.weather.data.datasources.local.WeatherForeCastLocalDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.IGeoCodeRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherMapGeoCodeRemoteDataSourceImpl
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import com.hrudhaykanth116.weather.data.repository.GeoCodeRepositoryImpl
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.data.repository.WeatherForeCastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideForeCastRepository(
        remoteDataSource: WeatherForeCastRemoteDataSource,
        localDataSource: WeatherForeCastLocalDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): IWeatherForeCastRepository = WeatherForeCastRepositoryImpl(
        remoteDataSource,
        localDataSource,
        ioDispatcher
    )

    @Provides
    fun provideGeoCodingRemoteDataSource(
        openWeatherApiService: OpenWeatherApiService,
    ): IGeoCodeRemoteDataSource = WeatherMapGeoCodeRemoteDataSourceImpl(
        openWeatherApiService
    )

    @Provides
    fun provideGeoCodingRepository(
        geoCodeRemoteDataSource: IGeoCodeRemoteDataSource,
    ): IGeoCodeRepository = GeoCodeRepositoryImpl(
        geoCodeRemoteDataSource
    )

}