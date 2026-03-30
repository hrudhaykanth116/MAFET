package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.weather.resources.Res
import com.hrudhaykanth116.weather.resources.ic_atmosphere
import com.hrudhaykanth116.weather.resources.ic_element_clouds
import com.hrudhaykanth116.weather.resources.ic_element_dew
import com.hrudhaykanth116.weather.resources.ic_element_feels_lik
import com.hrudhaykanth116.weather.resources.ic_element_himidity
import com.hrudhaykanth116.weather.resources.ic_element_pressure
import com.hrudhaykanth116.weather.resources.ic_element_sunrise
import com.hrudhaykanth116.weather.resources.ic_element_sunset
import com.hrudhaykanth116.weather.resources.ic_element_temperature
import com.hrudhaykanth116.weather.resources.ic_element_uvi
import com.hrudhaykanth116.weather.resources.ic_element_visibility
import com.hrudhaykanth116.weather.resources.ic_element_wind_deg
import com.hrudhaykanth116.weather.resources.ic_element_wind_speed
import org.jetbrains.compose.resources.DrawableResource

class GetWeatherElementIconUseCase {
    operator fun invoke(element: WeatherElement): DrawableResource {
        return when (element) {
            WeatherElement.DEW_POINT -> Res.drawable.ic_atmosphere
            WeatherElement.FEELS_LIKE -> Res.drawable.ic_atmosphere
            WeatherElement.HUMIDITY -> Res.drawable.ic_atmosphere
            WeatherElement.PRESSURE -> Res.drawable.ic_atmosphere
            WeatherElement.CLOUDS -> Res.drawable.ic_atmosphere
            WeatherElement.SUNRISE -> Res.drawable.ic_atmosphere
            WeatherElement.SUNSET -> Res.drawable.ic_atmosphere
            WeatherElement.TEMP -> Res.drawable.ic_atmosphere
            WeatherElement.UVI -> Res.drawable.ic_atmosphere
            WeatherElement.VISIBILITY -> Res.drawable.ic_atmosphere
            WeatherElement.WIND_DEG -> Res.drawable.ic_atmosphere
            WeatherElement.WIND_SPEED -> Res.drawable.ic_atmosphere
        }
    }
}

data class WeatherElementUIState(
    val weatherElement: WeatherElement,
    val value: UIText,
)

enum class WeatherElement(
    val id: String,
    val displayName: UIText,
    val displayIcon: DrawableResource,
    val definition: UIText
) {
    DEW_POINT(
        "dewPoint",
        UIText.Text("Dew point"),
        Res.drawable.ic_element_dew,
        "The dew point of a given body of air is the temperature to which it must be cooled to become saturated with water vapor.".toUIText()
    ),
    FEELS_LIKE(
        "feelsLike",
        UIText.Text("Feels like"),
        Res.drawable.ic_element_feels_lik,
        "Temperature equivalent perceived by humans, caused by the combined effects of air temperature, relative humidity and wind speed.".toUIText()
    ),
    HUMIDITY(
        "humidity",
        UIText.Text("Humidity"),
        Res.drawable.ic_element_himidity,
        "temperature equivalent perceived by humans, caused by the combined effects of air temperature, relative humidity and wind speed.".toUIText()
    ),
    PRESSURE(
        "pressure",
        UIText.Text("Pressure"),
        Res.drawable.ic_element_pressure,
        "Force in an area pushed against a surface by the weight of the atmosphere of Earth, a layer of air.".toUIText()
    ),
    CLOUDS(
        "Clouds",
        UIText.Text("Clouds"),
        Res.drawable.ic_element_clouds,
        "Cloudiness".toUIText()
    ),
    SUNRISE(
        "sunrise",
        UIText.Text("Sunrise"),
        Res.drawable.ic_element_sunrise,
        "Time at which Sun rises".toUIText()
    ),
    SUNSET(
        "sunset",
        UIText.Text("Sunset"),
        Res.drawable.ic_element_sunset,
        "Time at which Sun sets".toUIText()
    ),
    TEMP(
        "temp",
        UIText.Text("Temp"),
        Res.drawable.ic_element_temperature,
        "Hotness or Coldness measurement".toUIText()
    ),
    UVI(
        "uvi",
        UIText.Text("UVI"),
        Res.drawable.ic_element_uvi,
        "Strength of Sun burn producing Ultraviolet radiation.".toUIText()
    ),
    VISIBILITY(
        "visibility",
        UIText.Text("Visibility"),
        Res.drawable.ic_element_visibility,
        "how far a normal person can see depending on the weather(Max 10 mts)".toUIText()
    ),
    WIND_DEG(
        "windDeg",
        UIText.Text("Wind deg"),
        Res.drawable.ic_element_wind_deg,
        "Wind direction.".toUIText()
    ),
    WIND_SPEED(
        "windSpeed",
        UIText.Text("Wind speed"),
        Res.drawable.ic_element_wind_speed,
        "Speed of the wind".toUIText()
    ),
}
