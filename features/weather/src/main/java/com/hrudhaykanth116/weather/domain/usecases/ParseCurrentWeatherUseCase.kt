package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.number.truncateToDecimals
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.models.CurrentWeatherUIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParseCurrentWeatherUseCase @Inject constructor(
    private val dateTimeUtils: DateTimeUtils,
    private val temperatureConverter: TemperatureConverter,
    private val getWeatherIconUseCase: GetWeatherIconUseCase,
) {

    suspend operator fun invoke(current: WeatherForeCastResponse.Current?): CurrentWeatherUIState {

        // current ?: return CurrentWeatherUIState(
        //     clouds = "- -".toUIText(),
        //     dewPoint = "- -".toUIText(),
        //     dt = "- -".toUIText(),
        //     feelsLike = "- -".toUIText(),
        //     humidity = "- -".toUIText(),
        //     pressure = "- -".toUIText(),
        //     sunrise = "- -".toUIText(),
        //     sunset = "- -".toUIText(),
        //     temp = "- -".toUIText(),
        //     uvi = "- -".toUIText(),
        //     visibility = "- -".toUIText(),
        //     weather = null,
        //     windDeg = "- -".toUIText(),
        //     windSpeed = "- -".toUIText()
        //
        // )

        val currentWeather = current?.weather?.firstOrNull()

        val humidity = current?.humidity.toUIText("- -")
        val sunrise = dateTimeUtils.getTimeFromSecs(current?.sunrise).toUIText("- -")
        val sunset = dateTimeUtils.getTimeFromSecs(current?.sunset).toUIText("- -")
        val windSpeed = current?.windSpeed.toUIText("- -")
        val clouds = current?.clouds.toUIText("- -")
        val pressure = current?.pressure.toUIText("- -")
        val temp = temperatureConverter.getCelsiusFromKelvin(current?.temp)
            ?.truncateToDecimals(1).toUIText("- -")
        val dewPoint = current?.dewPoint.toUIText("- -")
        val dt = dateTimeUtils.getDateFromSecs(current?.dt).orEmpty().toUIText()
        val feelsLike = current?.feelsLike.toUIText("- -")
        val uvi = current?.uvi.toUIText("- -")
        val visibility = current?.visibility.toUIText("- -")
        val windDeg = current?.windDeg.toUIText("- -")

        return CurrentWeatherUIState(
            // Temperature in Kelvin
            // Wind speed in meter/sec
            // Pressure hPa
            // Clouds %
            // rain mm/h
            // HUmidity %
            // Assuming non null values.
            time = dt,
            weather = CurrentWeatherUIState.Weather(
                main = currentWeather?.main.replaceIfBlank("- -").toUIText(),
                description = currentWeather?.description.replaceIfBlank("- -").toUIText(),
                icon = getWeatherIconUseCase(currentWeather?.id).toImageHolder(),
            ),
            // TODO: Optimise code.
            weatherElementUIState = listOf<WeatherElementUIState>(
                WeatherElementUIState(WeatherElement.DEW_POINT, dewPoint),
                WeatherElementUIState(WeatherElement.FEELS_LIKE, feelsLike),
                WeatherElementUIState(WeatherElement.HUMIDITY, humidity),
                WeatherElementUIState(WeatherElement.PRESSURE, pressure),
                WeatherElementUIState(WeatherElement.CLOUDS, clouds),
                WeatherElementUIState(WeatherElement.SUNRISE, sunrise),
                WeatherElementUIState(WeatherElement.SUNSET, sunset),
                WeatherElementUIState(WeatherElement.TEMP, temp),
                WeatherElementUIState(WeatherElement.UVI, uvi),
                WeatherElementUIState(WeatherElement.VISIBILITY, visibility),
                WeatherElementUIState(WeatherElement.WINDEG, windDeg),
                WeatherElementUIState(WeatherElement.WINDSPEED, windSpeed),
            ),
        )

    }

}