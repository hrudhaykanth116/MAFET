package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.common.utils.conversions.TemperatureConverter
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.number.truncateToDecimals
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.domain.models.CurrentWeatherUIState
import com.hrudhaykanth116.weather.domain.models.WeatherListItemUIState
import javax.inject.Inject
import javax.inject.Singleton
import com.hrudhaykanth116.core.R as CoreR

@Singleton
class ParseCurrentWeatherUseCase @Inject constructor(
    private val dateTimeUtils: DateTimeUtils,
    private val temperatureConverter: TemperatureConverter,
) {

    suspend operator fun invoke(current: WeatherForeCastResponse.Current?): CurrentWeatherUIState {

        current ?: return CurrentWeatherUIState(
            clouds = "- -".toUIText(),
            dewPoint = "- -".toUIText(),
            dt = "- -".toUIText(),
            feelsLike = "- -".toUIText(),
            humidity = "- -".toUIText(),
            pressure = "- -".toUIText(),
            sunrise = "- -".toUIText(),
            sunset = "- -".toUIText(),
            temp = "- -".toUIText(),
            uvi = "- -".toUIText(),
            visibility = "- -".toUIText(),
            weather = null,
            windDeg = "- -".toUIText(),
            windSpeed = "- -".toUIText()

        )

        return CurrentWeatherUIState(
            // Temperature in Kelvin
            // Wind speed in meter/sec
            // Pressure hPa
            // Clouds %
            // rain mm/h
            // HUmidity %
            // Assuming non null values.
            weather = CurrentWeatherUIState.Weather(
                main = current.weather?.firstOrNull()?.main.orEmpty().toUIText(),
                id = current.weather?.firstOrNull()?.id?.toString().orEmpty().toUIText(),
                description = current.weather?.firstOrNull()?.description.orEmpty().toUIText(),
                icon = current.weather?.firstOrNull()?.icon?.toUrlImageHolder() ?: CoreR.drawable.ic_exclamation.toImageHolder(),
            ),
            humidity = current.humidity?.toString().orEmpty().toUIText(),
            sunrise = dateTimeUtils.getTimeFromSecs(current.sunrise!!).toUIText(),
            sunset = dateTimeUtils.getTimeFromSecs(current.sunset!!).toUIText(),
            windSpeed = current.windSpeed?.toString().orEmpty().toUIText(),
            clouds = current.clouds?.toString().orEmpty().toUIText(),
            pressure = current.pressure?.toString().orEmpty().toUIText(),
            temp = temperatureConverter.getCelsiusFromKelvin(current.temp)
                ?.truncateToDecimals(1).orEmpty().toUIText(),
            dewPoint = current.dewPoint?.toString().orEmpty().toUIText(),
            dt = dateTimeUtils.getDateFromSecs(current.dt!!).toUIText(),
            feelsLike = current.feelsLike?.toString().orEmpty().toUIText(),
            uvi = current.uvi?.toString().orEmpty().toUIText(),
            visibility = current.visibility?.toString().orEmpty().toUIText(),
            windDeg = current.windDeg?.toString().orEmpty().toUIText(),

        )

    }

}