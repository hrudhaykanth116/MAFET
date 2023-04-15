package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.auth.data.repositories.AuthRepository
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String?,
        pwd: String?,
    ): com.hrudhaykanth116.core.data.models.DataResult<Unit> {

        if (email.isNullOrEmpty()) {
            return com.hrudhaykanth116.core.data.models.DataResult.Error(uiMessage = com.hrudhaykanth116.core.data.models.UIText.Text("Email cannot be empty."))
        }
        if (pwd.isNullOrEmpty()) {
            return com.hrudhaykanth116.core.data.models.DataResult.Error(uiMessage = com.hrudhaykanth116.core.data.models.UIText.Text("Password cannot be empty."))
        }

        return when (val loginResult = authRepository.login(email, pwd)) {
            is com.hrudhaykanth116.core.data.models.DataResult.Error -> com.hrudhaykanth116.core.data.models.DataResult.Error(uiMessage = loginResult.uiMessage)
            is com.hrudhaykanth116.core.data.models.DataResult.Success -> com.hrudhaykanth116.core.data.models.DataResult.Success(Unit)
        }
    }

}