package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.weather.data.datasources.local.WeatherForeCastLocalDataSource
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherForeCastRepositoryImpl @Inject constructor(
    // TODO: Implement abstraction depending on the use case
    private val weatherForeCastRemoteDataSource: WeatherForeCastRemoteDataSource,
    private val weatherForeCastLocalDataSource: WeatherForeCastLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : IWeatherForeCastRepository {


    override suspend fun getDailyWeatherForeCast(
        latitude: String, longitude: String,
    ): DataResult<WeatherForeCastResponse> = withContext(dispatcher) {
        weatherForeCastRemoteDataSource.getWeatherForeCast(latitude, longitude)
    }


}