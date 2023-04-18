package com.hrudhaykanth116.mafet.home

import com.hrudhaykanth116.core.ui.StatefulViewModel
import com.hrudhaykanth116.mafet.home.models.HomeScreenEffect
import com.hrudhaykanth116.mafet.home.models.HomeScreenEvent
import com.hrudhaykanth116.mafet.home.models.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : StatefulViewModel<HomeScreenState, HomeScreenEffect, HomeScreenEvent>(
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