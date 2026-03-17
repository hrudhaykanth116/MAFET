package com.hrudhaykanth116.auth.domain.usecases

import com.hrudhaykanth116.core.data.models.UIText

class ValidatePasswordUseCase {

    operator fun invoke(password: String?): UIText? {

        return if (password.isNullOrBlank()) {
            UIText.Text("Password cannot be empty.")
        } else if (password.length < 6) {
            UIText.Text("Password length should be greater than 5")
        } else {
            null
        }
    }

}