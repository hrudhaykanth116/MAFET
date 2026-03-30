package com.hrudhaykanth116.auth.domain.usecases

import android.util.Patterns
import com.hrudhaykanth116.core.ui.models.UIText

class ValidateEmailUseCase {

    operator fun invoke(email: String?): UIText? {

        return if (
            email.isNullOrBlank()
        ) {
            UIText.Text("Email cannot be empty")
        } else if (
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            UIText.Text("Not valid email format")
        } else {
            null
        }

    }

}