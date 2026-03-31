package com.hrudhaykanth116.composeapp.home.dashboard.models

data class DashboardScreenState(
    val todoSummary: TodoSummary? = null,
    val weatherSummary: WeatherSummary? = null,
    val tvSummary: TvSummary? = null
)
