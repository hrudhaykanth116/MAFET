package com.hrudhaykanth116.auth.domain.usecases

import com.hrudhaykanth116.auth.data.models.LoginRequest
import com.hrudhaykanth116.auth.data.models.LoginResult
import com.hrudhaykanth116.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.auth.domain.models.login.LoginScreenState
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.models.UIText

class LoginUseCase(
    private val authRepository: IAuthRepository,
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

        val loginResult: RepoResultWrapper<LoginResult> = authRepository.login(
            LoginRequest(
                email, password
            )
        )

        return when (loginResult) {
            is RepoResultWrapper.Error -> {
                loginUIState.copy(
                    // TODO: error handling
                    // loginError = loginResult.errorState.mapToUIText()
                )
            }

            is RepoResultWrapper.Success -> {
                loginUIState.copy(
                    isLoggedIn = true
                )
            }
        }

    }

}