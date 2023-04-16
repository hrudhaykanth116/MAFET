package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.mafet.auth.domain.models.LoginScreenEffect
import com.hrudhaykanth116.mafet.auth.domain.usecases.LoginUseCase
import com.hrudhaykanth116.mafet.auth.domain.models.LoginScreenEvent
import com.hrudhaykanth116.mafet.auth.domain.models.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : UDFViewModel<LoginScreenState, LoginScreenEffect, LoginScreenEvent>(
    LoginScreenState()
) {

    override fun processEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.Login -> {
                login()
            }
            is LoginScreenEvent.EmailChanged -> {
                setState {
                    copy(
                        loginEmail = event.email
                    )
                }
            }
            is LoginScreenEvent.PasswordChanged -> {
                setState {
                    copy(
                        loginPassword = event.pwd
                    )
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val newState: LoginScreenState = loginUseCase(
                state
            )

            setState {
                newState
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}