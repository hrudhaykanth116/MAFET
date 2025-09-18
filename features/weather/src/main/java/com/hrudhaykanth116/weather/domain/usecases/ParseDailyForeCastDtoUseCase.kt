package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils.Companion.DAY_DATE_FORMAT
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class ParseDailyForeCastDtoUseCase @Inject constructor(
    private val dateTimeUtils: DateTimeUtils,
    private val temperatureConverter: TemperatureConverter,
    private val getWeatherIconUseCase: GetWeatherIconUseCase,
) {

    // hrudhay_check_list: Use DI to inject this
    private val dispatcher = Dispatchers.Default

    suspend operator fun invoke(data: WeatherForeCastResponse): List<DailyWeatherUIState> =
        withContext(dispatcher) {
            val dailyData = data.daily ?: return@withContext listOf()

            val weatherElementUIStates = mutableListOf<DailyWeatherUIState>()

            dailyData.forEach { dayDataNullable ->
                dayDataNullable?.let { dayData ->
                    getWeatherListItemUIState(dayData)?.let { weatherListItemUIState ->
                        weatherElementUIStates.add(
                            weatherListItemUIState
                        )
                    }

                }
            }

            return@withContext weatherElementUIStates
        }

    private fun getWeatherListItemUIState(dayData: WeatherForeCastResponse.Daily): DailyWeatherUIState? {

        val weatherMain = dayData.weather?.firstOrNull() ?: return null

        return DailyWeatherUIState(
            // Temperature in Kelvin
            // Wind speed in meter/sec
            // Pressure hPa
            // Clouds %
            // rain mm/h
            // HUmidity %
            // Assuming non null values.
            weatherElementsList = listOf(),
            weatherMain = WeatherMain(
                description = weatherMain.description.replaceIfBlank("- -").toUIText(),
                icon = getWeatherIconUseCase(weatherMain.id),
                title = weatherMain.main.replaceIfBlank("- -").toUIText()

            ),
            time = dayData.dt?.milliseconds?.inWholeMilliseconds?.let { dateTimeUtils.getFormattedDateTime(it, "HH:mm:ss") }
                .replaceIfBlank("- -").toUIText(),
        )
    }

}