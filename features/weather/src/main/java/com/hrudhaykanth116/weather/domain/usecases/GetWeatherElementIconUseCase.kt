package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherElementIconUseCase @Inject constructor(

) {
    operator fun invoke(element: WeatherElement): Int {

        return when (element) {
            WeatherElement.DEW_POINT -> R.drawable.ic_atmosphere
            WeatherElement.FEELS_LIKE -> R.drawable.ic_atmosphere
            WeatherElement.HUMIDITY -> R.drawable.ic_atmosphere
            WeatherElement.PRESSURE -> R.drawable.ic_atmosphere
            WeatherElement.CLOUDS -> R.drawable.ic_atmosphere
            WeatherElement.SUNRISE -> R.drawable.ic_atmosphere
            WeatherElement.SUNSET -> R.drawable.ic_atmosphere
            WeatherElement.TEMP -> R.drawable.ic_atmosphere
            WeatherElement.UVI -> R.drawable.ic_atmosphere
            WeatherElement.VISIBILITY -> R.drawable.ic_atmosphere
            WeatherElement.WIND_DEG -> R.drawable.ic_atmosphere
            WeatherElement.WIND_SPEED -> R.drawable.ic_atmosphere
        }

    }

}

// TODO: Extract out these classes
data class WeatherElementUIState(
    val weatherElement: WeatherElement,
    val value: UIText,
)

enum class WeatherElement(val id: String, val displayName: UIText, val displayIcon: ImageHolder, val definition: UIText) {

    DEW_POINT("dewPoint", UIText.Text("Dew point"), R.drawable.ic_element_dew.toImageHolder(), "The dew point of a given body of air is the temperature to which it must be cooled to become saturated with water vapor.".toUIText()),
    FEELS_LIKE("feelsLike", UIText.Text("Feels like"), R.drawable.ic_element_feels_lik.toImageHolder(), "Temperature equivalent perceived by humans, caused by the combined effects of air temperature, relative humidity and wind speed.".toUIText()),
    HUMIDITY("humidity", UIText.Text("Humidity"), R.drawable.ic_element_himidity.toImageHolder(), "temperature equivalent perceived by humans, caused by the combined effects of air temperature, relative humidity and wind speed.".toUIText()),
    PRESSURE("pressure", UIText.Text("Pressure"), R.drawable.ic_element_pressure.toImageHolder(), "Force in an area pushed against a surface by the weight of the atmosphere of Earth, a layer of air.".toUIText()),
    CLOUDS("Clouds", UIText.Text("Clouds"), R.drawable.ic_element_clouds.toImageHolder(), "Cloudiness".toUIText()),
    SUNRISE("sunrise", UIText.Text("Sunrise"), R.drawable.ic_element_sunrise.toImageHolder(), "Time at which Sun rises".toUIText()),
    SUNSET("sunset", UIText.Text("Sunset"), R.drawable.ic_element_sunset.toImageHolder(), "Time at which Sun sets".toUIText()),
    TEMP("temp", UIText.Text("Temp"), R.drawable.ic_element_temperature.toImageHolder(), "Hotness or Coldness measurement".toUIText()),
    UVI("uvi", UIText.Text("UVI"), R.drawable.ic_element_uvi.toImageHolder(), "Strength of Sun burn producing Ultraviolet radiation.".toUIText()),
    VISIBILITY("visibility", UIText.Text("Visibility"), R.drawable.ic_element_visibility.toImageHolder(), "how far a normal person can see depending on the weather(Max 10 mts)".toUIText()),
    WIND_DEG("windDeg", UIText.Text("Wind deg"), R.drawable.ic_element_wind_deg.toImageHolder(), "Wind direction.".toUIText()),
    WIND_SPEED("windSpeed", UIText.Text("Wind speed"), R.drawable.ic_element_wind_speed.toImageHolder(), "Speed of the wind".toUIText()),

}