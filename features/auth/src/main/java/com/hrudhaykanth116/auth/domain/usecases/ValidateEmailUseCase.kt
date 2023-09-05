package com.hrudhaykanth116.auth.domain.usecases

import android.util.Patterns
import com.hrudhaykanth116.core.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateEmailUseCase @Inject constructor(

) {

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