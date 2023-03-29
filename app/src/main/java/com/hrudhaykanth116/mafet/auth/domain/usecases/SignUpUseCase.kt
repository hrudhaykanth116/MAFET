package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.auth.data.data_models.SignUpResponse
import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
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
    ): DataResult<UIText> {

        val signUpResponseResult: DataResult<SignUpResponse> =
            authRepository.signUp(name, email, password)
        return when (signUpResponseResult) {
            is DataResult.Error -> {
                DataResult.Error(
                    uiMessage = signUpResponseResult.uiMessage
                )
            }
            is DataResult.Success -> {
                DataResult.Success(
                    UIText.Text("Successfully signed up.")
                )
            }
        }
    }
}