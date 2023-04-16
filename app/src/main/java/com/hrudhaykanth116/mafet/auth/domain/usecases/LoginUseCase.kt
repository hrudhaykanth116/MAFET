package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.mafet.auth.data.models.LoginRequest
import com.hrudhaykanth116.mafet.auth.data.models.LoginResult
import com.hrudhaykanth116.mafet.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.mafet.auth.domain.models.LoginScreenState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {

    suspend operator fun invoke(loginUIState: LoginScreenState): LoginScreenState {

        val email = loginUIState.loginEmail.text
        val password = loginUIState.loginPassword.text

        if (email.isBlank() || password.isBlank()) {
            return loginUIState.copy(
                // emailErrorMessage = UIText.Text("Email cannot be empty"),
                // passwordErrorMessage = UIText.Text("Password cannot be empty")
                loginError = UIText.Text("Please check email or password is not empty.")
            )
        }

        val loginResult: DataResult<LoginResult> = authRepository.login(
            LoginRequest(
                email, password
            )
        )

        return when (loginResult) {
            is DataResult.Error -> {
                loginUIState.copy(
                    loginError = loginResult.uiMessage
                )
            }
            is DataResult.Success -> {
                loginUIState.copy(
                    isLoggedIn = true
                )
            }
        }

    }

}