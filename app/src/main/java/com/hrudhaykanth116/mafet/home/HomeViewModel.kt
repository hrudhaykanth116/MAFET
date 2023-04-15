package com.hrudhaykanth116.mafet.home

import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.core.ui.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : com.hrudhaykanth116.core.ui.StatefulViewModel<HomeScreenState, HomeScreenEffect, HomeScreenEvent>(HomeScreenState()) {
    override fun processEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.LogOut -> {
                authRepository.saveIsLoggedIn(false)
                setEffect(HomeScreenEffect.OnLogout)
            }
        }
    }

}