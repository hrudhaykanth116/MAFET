package com.hrudhaykanth116.weather.domain.repository

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse

interface IWeatherForeCastRepository {

    suspend fun getDailyWeatherForeCast(
        latitude: String,
        longitude: String
    ): DomainResult<WeatherForeCastResponse>
}
