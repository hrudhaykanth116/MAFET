package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.auth.data.data_models.SignUpResponse
import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): com.hrudhaykanth116.core.data.models.DataResult<com.hrudhaykanth116.core.data.models.UIText> {

        val signUpResponseResult: com.hrudhaykanth116.core.data.models.DataResult<SignUpResponse> =
            authRepository.signUp(name, email, password)
        return when (signUpResponseResult) {
            is com.hrudhaykanth116.core.data.models.DataResult.Error -> {
                com.hrudhaykanth116.core.data.models.DataResult.Error(
                    uiMessage = signUpResponseResult.uiMessage
                )
            }
            is com.hrudhaykanth116.core.data.models.DataResult.Success -> {
                com.hrudhaykanth116.core.data.models.DataResult.Success(
                    com.hrudhaykanth116.core.data.models.UIText.Text("Successfully signed up.")
                )
            }
        }
    }
}