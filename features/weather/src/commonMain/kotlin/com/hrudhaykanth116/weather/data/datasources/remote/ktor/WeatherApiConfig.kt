package com.hrudhaykanth116.weather.data.datasources.remote.ktor

expect object WeatherApiConfig {
    val geoCodingApiKey: String
    val forecastApiKey: String
}
