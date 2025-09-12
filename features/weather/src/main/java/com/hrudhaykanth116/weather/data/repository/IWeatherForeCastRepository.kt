package com.hrudhaykanth116.weather.data.repository

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse

interface IWeatherForeCastRepository {

    suspend fun getDailyWeatherForeCast(latitude: String, longitude: String): RepoResultWrapper<WeatherForeCastResponse>

}