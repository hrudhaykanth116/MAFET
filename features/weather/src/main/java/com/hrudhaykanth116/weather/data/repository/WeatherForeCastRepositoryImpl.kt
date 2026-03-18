package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.data.repository.BaseRepository
import com.hrudhaykanth116.weather.data.datasources.remote.WeatherForeCastRemoteDataSource
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import kotlinx.coroutines.CoroutineDispatcher

class WeatherForeCastRepositoryImpl(
    // hrudhay_check_list: Implement abstraction depending on the use case
    private val weatherForeCastRemoteDataSource: WeatherForeCastRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), IWeatherForeCastRepository {


    override suspend fun getDailyWeatherForeCast(
        latitude: String, longitude: String,
    ): RepoResultWrapper<WeatherForeCastResponse> = getResult {
        weatherForeCastRemoteDataSource.getWeatherForeCast(latitude, longitude)
    }


}