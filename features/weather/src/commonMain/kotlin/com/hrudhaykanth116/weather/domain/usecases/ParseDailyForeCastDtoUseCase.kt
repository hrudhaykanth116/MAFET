package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils.Companion.DAY_DATE_FORMAT
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherMain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

class ParseDailyForeCastDtoUseCase(
    private val dateTimeUtils: DateTimeUtils,
    private val temperatureConverter: TemperatureConverter,
    private val getWeatherIconUseCase: GetWeatherIconUseCase,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(data: WeatherForeCastResponse): List<DailyWeatherUIState> =
        withContext(dispatcher) {
            val dailyData = data.daily ?: return@withContext listOf()

            val weatherElementUIStates = mutableListOf<DailyWeatherUIState>()

            dailyData.forEach { dayDataNullable ->
                dayDataNullable?.let { dayData ->
                    getWeatherListItemUIState(dayData)?.let { weatherListItemUIState ->
                        weatherElementUIStates.add(weatherListItemUIState)
                    }
                }
            }

            return@withContext weatherElementUIStates
        }

    private fun getWeatherListItemUIState(dayData: WeatherForeCastResponse.Daily): DailyWeatherUIState? {
        val weatherMain = dayData.weather?.firstOrNull() ?: return null

        return DailyWeatherUIState(
            weatherElementsList = listOf(),
            weatherMain = WeatherMain(
                description = weatherMain.description.replaceIfBlank("- -").toUIText(),
                icon = getWeatherIconUseCase(weatherMain.id),
                title = weatherMain.main.replaceIfBlank("- -").toUIText()
            ),
            time = dayData.dt?.seconds?.inWholeMilliseconds?.let { dateTimeUtils.getFormattedDateTime(it, DAY_DATE_FORMAT) }
                .replaceIfBlank("- -").toUIText(),
        )
    }

}
