package com.hrudhaykanth116.composeapp.home

import com.hrudhaykanth116.core.ui.viewmodels.StatefulViewModel
import com.hrudhaykanth116.composeapp.home.models.HomeScreenEffect
import com.hrudhaykanth116.composeapp.home.models.HomeScreenEvent
import com.hrudhaykanth116.composeapp.home.models.HomeScreenState

class HomeViewModel : StatefulViewModel<HomeScreenState, HomeScreenEffect, HomeScreenEvent>(
    HomeScreenState()
) {
    override fun processEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.LogOut -> {
                // authRepository.saveIsLoggedIn(false)
                // setEffect(HomeScreenEffect.OnLogout)
            }
        }
    }

}
