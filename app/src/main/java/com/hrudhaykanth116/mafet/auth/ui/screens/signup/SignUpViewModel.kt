package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpEffect
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormEvent
import com.hrudhaykanth116.mafet.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.mafet.auth.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signupUseCase: SignUpUseCase,
) : com.hrudhaykanth116.core.ui.StatefulViewModel<SignUpFormState, SignUpEffect, SignUpFormEvent>(
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