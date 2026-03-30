package com.hrudhaykanth116.auth.domain.usecases

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.auth.domain.models.login.LoginScreenState
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.models.UIText

class LoginUseCase(
    private val authRepository: IAuthRepository,
) {

    suspend operator fun invoke(loginUIState: LoginScreenState): LoginScreenState {

        val email = loginUIState.loginEmail.text
        val password = loginUIState.loginPassword.text

        if (email.isBlank() || password.isBlank()) {
            return loginUIState.copy(
                loginError = UIText.Text("Please check email or password is not empty.")
            )
        }

        val loginResult: DomainResult<LoginResult> = authRepository.login(
            LoginRequest(
                email, password
            )
        )

        return when (loginResult) {
            is DomainResult.Error -> {
                loginUIState.copy(
                    loginError = UIText.Text(loginResult.error.toMessage())
                )
            }

            is DomainResult.Success -> {
                loginUIState.copy(
                    isLoggedIn = true
                )
            }
        }

    }

}