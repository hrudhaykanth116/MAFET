package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse

interface IWeatherForeCastRepository {

    suspend fun getDailyWeatherForeCast(latitude: String, longitude: String): DataResult<WeatherForeCastResponse>

}