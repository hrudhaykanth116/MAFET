package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.R
import com.hrudhaykanth116.mafet.auth.domain.usecases.SignUpUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidateEmailUseCase
import com.hrudhaykanth116.mafet.auth.domain.usecases.ValidatePasswordUseCase
import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
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

            val signUpFormState = stateFlow.value
            val email = signUpFormState.email.text
            val pwd = signUpFormState.password.text
            val repeatedPwd = signUpFormState.repeatedPassword.text

            val validateEmailResult = validateEmailUseCase(email)
            val validatePwdResult = validatePasswordUseCase(pwd)

            val validateReEnteredPwdResult = if (pwd != repeatedPwd) {
                DataResult.Error(
                    uiMessage = UIText.StringRes(R.string.passwords_do_not_match)
                )
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
                    emailError = if (validateEmailResult is DataResult.Error) validateEmailResult.uiMessage else null,
                    passwordError = if (validatePwdResult is DataResult.Error) validatePwdResult.uiMessage else null
                )
            }

            if (!hasError) {

                val dataResult: DataResult<UIText> = signUpUseCase(
                    email,
                    email,
                    pwd
                )

                when (dataResult) {
                    is DataResult.Error -> {
                        setEffect(
                            SignUpEffect.Error(
                                dataResult.uiMessage
                                    ?: UIText.StringRes(R.string.something_went_wrong)
                            )
                        )
                    }
                    is DataResult.Success -> {
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