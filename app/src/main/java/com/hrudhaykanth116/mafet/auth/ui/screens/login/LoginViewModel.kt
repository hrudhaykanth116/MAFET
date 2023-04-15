package com.hrudhaykanth116.mafet.auth.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.auth.domain.usecases.LoginUseCase
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.ui.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : com.hrudhaykanth116.core.ui.StatefulViewModel<LoginScreenState, LoginScreenEffect, LoginScreenEvent>(LoginScreenState()) {

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
            val result: com.hrudhaykanth116.core.data.models.DataResult<Unit> = loginUseCase(
                email = stateFlow.value.loginEmail.text,
                pwd = stateFlow.value.loginPassword.text
            )
            when (result) {
                is com.hrudhaykanth116.core.data.models.DataResult.Success -> {
                    setEffect(LoginScreenEffect.LoggedIn)
                }
                is com.hrudhaykanth116.core.data.models.DataResult.Error -> {
                    setState {
                        copy(
                            loginError = result.uiMessage
                        )
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}