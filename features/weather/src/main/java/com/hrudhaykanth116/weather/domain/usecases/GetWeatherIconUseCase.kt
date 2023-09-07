package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.weather.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherIconUseCase @Inject constructor(

) {

    operator fun invoke(id: Int?): Int{

        // https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
        return when(id){
            // 2xx: Thunderstorm
            in 200..299 -> R.drawable.ic_thunderstorm
            // 3xx: Drizzle
            in 300..399 -> R.drawable.ic_drizzle
            // 5xx: Rain
            in 500..599 -> R.drawable.ic_rain
            // 6xx: Snow
            in 600..699 -> R.drawable.ic_snow
            // 7xx: Atmosphere
            in 700..799 -> R.drawable.ic_atmosphere
            // 800: Clear
            800 -> R.drawable.ic_clear
            // 80x: Clouds
            in 801..809 -> R.drawable.ic_clouds
            // If id is not available within given range.
            else -> R.drawable.ic_atmosphere
        }

    }

}