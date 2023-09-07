package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.number.truncateToDecimals
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.models.WeatherListItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParseDailyForeCastDtoUseCase @Inject constructor(
    private val dateTimeUtils: DateTimeUtils,
    private val temperatureConverter: TemperatureConverter,
) {

    // TODO: Use DI to inject this
    private val dispatcher = Dispatchers.Default

    suspend operator fun invoke(data: WeatherForeCastResponse): List<WeatherListItemUIState> =
        withContext(dispatcher) {
            val dailyData = data.daily ?: return@withContext listOf()

            val weatherListItemUIStates = mutableListOf<WeatherListItemUIState>()

            dailyData.forEach { dayDataNullable ->
                dayDataNullable?.let { dayData ->
                    weatherListItemUIStates.add(
                        getWeatherListItemUIState(dayData)
                    )
                }
            }

            return@withContext weatherListItemUIStates
        }

    private fun getWeatherListItemUIState(dayData: WeatherForeCastResponse.Daily) =
        WeatherListItemUIState(
            // Temperature in Kelvin
            // Wind speed in meter/sec
            // Pressure hPa
            // Clouds %
            // rain mm/h
            // HUmidity %
            // Assuming non null values.
            dateTimeUtils.getDateFromSecs(dayData.dt!!),
            weather = dayData.weather?.firstOrNull()?.main.orEmpty(),
            humidity = dayData.humidity?.toString().orEmpty(),
            rain = dayData.rain?.toString().orEmpty(),
            sunrise = dateTimeUtils.getTimeFromSecs(dayData.sunrise!!),
            sunset = dateTimeUtils.getTimeFromSecs(dayData.sunset!!),
            maxTemp = temperatureConverter.getCelsiusFromKelvin(dayData.temp?.max)
                ?.truncateToDecimals(1).orEmpty(),
            minTemp = temperatureConverter.getCelsiusFromKelvin(dayData.temp?.min)
                ?.truncateToDecimals(1).orEmpty(),
            windSpeed = dayData.windSpeed?.toString().orEmpty(),
            clouds = dayData.clouds?.toString().orEmpty(),
            pressure = dayData.pressure?.toString().orEmpty()
        )

}