package com.hrudhaykanth116.composeapp.home.dashboard.models

sealed class DashboardScreenEvent {
    data object Refresh : DashboardScreenEvent()
}
