package com.hrudhaykanth116.core.common.utils.conversions

class TemperatureConverter {

    fun getCelsiusFromKelvin(temperature: Float?): Float? {
        temperature ?: return null
        return temperature - 273.15f
    }

}