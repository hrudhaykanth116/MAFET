package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.R
import com.hrudhaykanth116.mafet.auth.domain.usecases.SignUpUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidateEmailUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidatePasswordUseCase
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signUpUseCase: SignUpUseCase,
) : com.hrudhaykanth116.core.ui.StatefulViewModel<SignUpFormState, SignUpEffect, SignUpFormEvent>(
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

            val signUpFormState = stateFlow.value
            val email = signUpFormState.email.text
            val pwd = signUpFormState.password.text
            val repeatedPwd = signUpFormState.repeatedPassword.text

            val validateEmailResult = validateEmailUseCase(email)
            val validatePwdResult = validatePasswordUseCase(pwd)

            val validateReEnteredPwdResult = if (pwd != repeatedPwd) {
                com.hrudhaykanth116.core.data.models.DataResult.Error(
                    uiMessage = com.hrudhaykanth116.core.data.models.UIText.StringRes(R.string.passwords_do_not_match)
                )
            } else {
                com.hrudhaykanth116.core.data.models.DataResult.Success("")
            }


            val hasError = listOf(
                validateEmailResult,
                validatePwdResult,
                validateReEnteredPwdResult
            ).any { it is com.hrudhaykanth116.core.data.models.DataResult.Error }

            setState {
                copy(
                    emailError = if (validateEmailResult is com.hrudhaykanth116.core.data.models.DataResult.Error) validateEmailResult.uiMessage else null,
                    passwordError = if (validatePwdResult is com.hrudhaykanth116.core.data.models.DataResult.Error) validatePwdResult.uiMessage else null
                )
            }

            if (!hasError) {

                val dataResult: com.hrudhaykanth116.core.data.models.DataResult<com.hrudhaykanth116.core.data.models.UIText> = signUpUseCase(
                    email,
                    email,
                    pwd
                )

                when (dataResult) {
                    is com.hrudhaykanth116.core.data.models.DataResult.Error -> {
                        setEffect(
                            SignUpEffect.Error(
                                dataResult.uiMessage
                                    ?: com.hrudhaykanth116.core.data.models.UIText.StringRes(R.string.something_went_wrong)
                            )
                        )
                    }
                    is com.hrudhaykanth116.core.data.models.DataResult.Success -> {
                        setEffect(
                            SignUpEffect.Success(
                                dataResult.data
                            )
                        )
                    }
                }
            }
        }
    }
}