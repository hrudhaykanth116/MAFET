package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateEmailUseCase @Inject constructor(

) {

    operator fun invoke(email: String?): com.hrudhaykanth116.core.data.models.DataResult<Unit> {
        if (!email.isNullOrBlank()) {
            if (email.contains("@")) {
                return com.hrudhaykanth116.core.data.models.DataResult.Success(Unit)
            } else {
                return com.hrudhaykanth116.core.data.models.DataResult.Error(
                    uiMessage = com.hrudhaykanth116.core.data.models.UIText.Text("Not valid email format")
                )
            }
        } else {
            return com.hrudhaykanth116.core.data.models.DataResult.Error(uiMessage = com.hrudhaykanth116.core.data.models.UIText.Text("Email cannot be empty"))
        }
    }

}