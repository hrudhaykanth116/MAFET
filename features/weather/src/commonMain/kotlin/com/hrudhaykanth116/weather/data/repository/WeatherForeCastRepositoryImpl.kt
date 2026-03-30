package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.repository.IWeatherForeCastRepository
import kotlinx.coroutines.CoroutineDispatcher

class WeatherForeCastRepositoryImpl(
    private val weatherForeCastRemoteDataSource: WeatherForeCastRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), IWeatherForeCastRepository {

    override suspend fun getDailyWeatherForeCast(
        latitude: String,
        longitude: String,
    ): DomainResult<WeatherForeCastResponse> =
        fetchResult {
            weatherForeCastRemoteDataSource.getWeatherForeCast(latitude, longitude)
        }
}
