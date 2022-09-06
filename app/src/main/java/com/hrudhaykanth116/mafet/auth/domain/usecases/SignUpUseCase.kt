package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.auth.data.data_models.SignUpResponse
import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.mafet.common.remote.DataResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(name: String, email: String, password: String): DataResource<SignUpResponse> {

        // Trying to perform business logic in use case.
        /*
        val validateEmailResult: DataResult = validateEmailUseCase(email)
        val validatePwdResult: DataResult = validatePasswordUseCase(password)

        val hasError = listOf<DataResult>(
            validateEmailResult,
            validatePwdResult
        ).any { it is DataResult.Error }


        setState {
            copy(
                emailError = if (validateEmailResult is DataResult.Error) validateEmailResult.errMsg else "",
                passwordError = if (validatePwdResult is DataResult.Error) validatePwdResult.errMsg else ""
            )
        }*/

        return authRepository.signUp(name, email, password)
    }
}