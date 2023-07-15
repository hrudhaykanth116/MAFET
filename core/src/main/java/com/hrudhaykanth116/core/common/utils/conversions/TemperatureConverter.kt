package com.hrudhaykanth116.weathertens.common.utils.conversions

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemperatureConverter @Inject constructor(

){

    fun getCelsiusFromKelvin(temperature: Float?): Float? {
        temperature ?: return null
        return temperature - 273.15f
    }

}