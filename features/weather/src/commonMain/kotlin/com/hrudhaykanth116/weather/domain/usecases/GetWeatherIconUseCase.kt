package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.weather.resources.Res
import com.hrudhaykanth116.weather.resources.ic_atmosphere
import com.hrudhaykanth116.weather.resources.ic_clear
import com.hrudhaykanth116.weather.resources.ic_clouds
import com.hrudhaykanth116.weather.resources.ic_drizzle
import com.hrudhaykanth116.weather.resources.ic_rain
import com.hrudhaykanth116.weather.resources.ic_snow
import com.hrudhaykanth116.weather.resources.ic_thunderstorm
import org.jetbrains.compose.resources.DrawableResource

class GetWeatherIconUseCase {

    operator fun invoke(id: Int?): DrawableResource {
        return when (id) {
            in 200..299 -> Res.drawable.ic_thunderstorm
            in 300..399 -> Res.drawable.ic_drizzle
            in 500..599 -> Res.drawable.ic_rain
            in 600..699 -> Res.drawable.ic_snow
            in 700..799 -> Res.drawable.ic_atmosphere
            800 -> Res.drawable.ic_clear
            in 801..809 -> Res.drawable.ic_clouds
            else -> Res.drawable.ic_atmosphere
        }
    }

}
