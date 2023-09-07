package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherElementIconUseCase @Inject constructor(

) {
    operator fun invoke(element: String): Int {

        return when (element) {
            "clouds" -> R.drawable.ic_atmosphere
            "dewPoint" -> R.drawable.ic_atmosphere
            "feelsLike" -> R.drawable.ic_atmosphere
            "humidity" -> R.drawable.ic_atmosphere
            "pressure" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            "clouds" -> R.drawable.ic_atmosphere
            else -> R.drawable.ic_atmosphere
        }

    }

}

data class WeatherElementUIState(
    val weatherElement: WeatherElement,
    val value: UIText,
)

enum class WeatherElement(val id: String, val displayName: UIText, val displayIcon: ImageHolder) {

    DEW_POINT("dewPoint", UIText.Text("Dew point"), R.drawable.ic_clouds.toImageHolder()),
    FEELS_LIKE("feelsLike", UIText.Text("Feels like"), R.drawable.ic_clouds.toImageHolder()),
    HUMIDITY("humidity", UIText.Text("Humidity"), R.drawable.ic_clouds.toImageHolder()),
    PRESSURE("pressure", UIText.Text("Pressure"), R.drawable.ic_clouds.toImageHolder()),
    CLOUDS("Clouds", UIText.Text("Clouds"), R.drawable.ic_clouds.toImageHolder()),
    SUNRISE("sunrise", UIText.Text("Sunrise"), R.drawable.ic_clouds.toImageHolder()),
    SUNSET("sunset", UIText.Text("Sunset"), R.drawable.ic_clouds.toImageHolder()),
    TEMP("temp", UIText.Text("Temp"), R.drawable.ic_clouds.toImageHolder()),
    UVI("uvi", UIText.Text("UVI"), R.drawable.ic_clouds.toImageHolder()),
    VISIBILITY("visibility", UIText.Text("Visibility"), R.drawable.ic_clouds.toImageHolder()),
    WINDEG("windDeg", UIText.Text("Wind deg"), R.drawable.ic_clouds.toImageHolder()),
    WINDSPEED("windSpeed", UIText.Text("Wind speed"), R.drawable.ic_clouds.toImageHolder()),

}