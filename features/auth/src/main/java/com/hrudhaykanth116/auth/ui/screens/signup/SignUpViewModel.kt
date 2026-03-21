package com.hrudhaykanth116.auth.ui.screens.signup

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.auth.domain.models.signup.SignUpEffect
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormEvent
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.auth.domain.usecases.SignUpUseCase
import com.hrudhaykanth116.core.ui.viewmodels.StatefulViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signupUseCase: SignUpUseCase,
) : StatefulViewModel<SignUpFormState, SignUpEffect, SignUpFormEvent>(
    SignUpFormState()
) {

    override fun processEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.EmailChanged -> {
                setState {
                    copy(emailTextFieldValue = event.email)
                }
            }

            is SignUpFormEvent.PasswordChanged -> {
                setState {
                    copy(passwordTextFieldValue = event.password)
                }
            }

            is SignUpFormEvent.ReEnteredPasswordChanged -> {
                setState {
                    copy(repeatedPassword = event.password)
                }
            }

            SignUpFormEvent.Submit -> {
                viewModelScope.launch {
                    setState {
                        copy(
                            isLoading = true
                        )
                    }
                    val newUIState = signupUseCase(state)
                    setState {
                        newUIState.copy(
                            isLoading = false
                        )
                    }
                }
            }

            is SignUpFormEvent.ProfileImageChanged -> {
                setState {
                    copy(imgBitmap = event.imgBitmap)
                }
            }

            is SignUpFormEvent.BioChanged -> {
                setState {
                    copy(bio = event.bio)
                }
            }

            is SignUpFormEvent.UserNameChanged -> {
                setState {
                    copy(userName = event.userName)
                }
            }

            is SignUpFormEvent.UserMessageShown -> {
                setState {
                    copy(
                        userMessage = null,
                    )
                }
            }
        }
    }


}