package com.hrudhaykanth116.auth.domain.usecases

import com.hrudhaykanth116.auth.data.models.SignUpRequest
import com.hrudhaykanth116.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.auth.domain.models.signup.SignUpFormState
import com.hrudhaykanth116.core.data.models.DataResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: IAuthRepository,
) {

    suspend operator fun invoke(signUpUIState: SignUpFormState): SignUpFormState {

        val newUIState = signUpUIState.getStateAfterValidation(
            validateEmailUseCase, validatePasswordUseCase
        )

        if (newUIState.containsError()) {
            return newUIState
        } else {
            val signUpResult = authRepository.signUp(
                SignUpRequest(
                    email = signUpUIState.emailTextFieldValue.text,
                    password = signUpUIState.passwordTextFieldValue.text,
                    userName = newUIState.userName.text,
                    imgBitmap = signUpUIState.imgBitmap,
                    bio = newUIState.bio.text,
                )
            )
            return when (signUpResult) {
                is DataResult.Error -> {
                    newUIState.copy(
                        userMessage = signUpResult.uiMessage
                    )
                }

                is DataResult.Success -> {
                    newUIState.copy(
                        isSignedUp = true
                    )
                }
            }
        }
    }
}