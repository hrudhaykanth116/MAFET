package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String?,
        pwd: String?,
    ): DataResult<Unit> {

        if (email.isNullOrEmpty()) {
            return DataResult.Error(uiMessage = UIText.Text("Email cannot be empty."))
        }
        if (pwd.isNullOrEmpty()) {
            return DataResult.Error(uiMessage = UIText.Text("Password cannot be empty."))
        }

        return when (val loginResult = authRepository.login(email, pwd)) {
            is DataResult.Error -> DataResult.Error(uiMessage = loginResult.uiMessage)
            is DataResult.Success -> DataResult.Success(Unit)
        }
    }

}