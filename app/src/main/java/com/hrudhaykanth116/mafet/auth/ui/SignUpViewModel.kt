package com.hrudhaykanth116.mafet.auth.ui

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.auth.domain.usecases.SignUpUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidateEmailUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidatePasswordUseCase
import com.hrudhaykanth116.mafet.auth.ui.effects.SignUpEffect
import com.hrudhaykanth116.mafet.auth.ui.events.SignUpFormEvent
import com.hrudhaykanth116.mafet.auth.ui.states.SignUpFormState
import com.hrudhaykanth116.mafet.common.data.data_models.DataResult
import com.hrudhaykanth116.mafet.common.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signUpUseCase: SignUpUseCase,
) : StatefulViewModel<SignUpFormState, SignUpEffect, SignUpFormEvent>(
    SignUpFormState()
) {


    override fun processEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.EmailChanged -> {
                setState {
                    copy(email = event.email)
                }
            }
            is SignUpFormEvent.PasswordChanged -> {
                setState {
                    copy(password = event.password)
                }
            }
            SignUpFormEvent.Submit -> {
                signUp()
            }
            is SignUpFormEvent.ReEnteredPasswordChanged -> {
                setState {
                    copy(repeatedPassword = event.password)
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {

            val signUpFormState = state.value
            val email = signUpFormState.email.text
            val pwd = signUpFormState.password.text
            val repeatedPwd = signUpFormState.repeatedPassword.text

            val validateEmailResult = validateEmailUseCase(email)
            val validatePwdResult = validatePasswordUseCase(pwd)

            val validateReEnteredPwdResult = if (pwd != repeatedPwd) {
                DataResult.Error("Passwords do not match")
            } else {
                DataResult.Success("")
            }


            val hasError = listOf(
                validateEmailResult,
                validatePwdResult,
                validateReEnteredPwdResult
            ).any { it is DataResult.Error }

            setState {
                copy(
                    // emailError = (validateEmailResult as? DataResult.Error)?.errMsg,
                    passwordError = if (validatePwdResult is DataResult.Error)
                        validatePwdResult.message
                    else
                        "",
                )
            }

            if (!hasError) {

                val dataResource = signUpUseCase(
                    email,
                    email,
                    pwd
                )

                if (dataResource.isSuccessful) {
                    setEffect(
                        SignUpEffect.Success(dataResource.data?.message)
                    )
                } else {
                    setEffect(
                        SignUpEffect.Error(dataResource.data?.message)
                    )
                }
            }
        }
    }
}